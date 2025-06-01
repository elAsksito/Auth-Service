package com.ask.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ask.auth.exception.InvalidCredentialsException;
import com.ask.auth.exception.IpBlockedException;
import com.ask.auth.exception.NotAuthenticatedException;
import com.ask.auth.exception.UserAlreadyExistsException;
import com.ask.auth.exception.UserNotFoundException;
import com.ask.auth.model.entity.Role;
import com.ask.auth.model.entity.User;
import com.ask.auth.model.enums.Action;
import com.ask.auth.payload.ErrorCodes;
import com.ask.auth.payload.request.LoginRequest;
import com.ask.auth.payload.response.TokenResponse;
import com.ask.auth.payload.response.UserResponse;
import com.ask.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleService roleService;
    private final AuditLogService auditLogService;
    private final TokenService tokenService;
    private final LoginEventService loginEventService;
    private final SuspiciousActivityService suspiciousActivityService;
    private final PasswordHistoryService passwordHistoryService;
    private final FailedLoginAttemptsIpService failedLoginAttemptsIpService;
    private final IpBlockService ipBlockService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Async
    @Transactional
    public CompletableFuture<User> createUserWithEmailAndPassword(LoginRequest request, String ipAddress,
            String userAgent) {
        return ipBlockService.isIpBlocked(ipAddress).thenCompose(isBlocked -> {
            if (isBlocked) {
                log.warn("IP {} is blocked, login attempt denied.", ipAddress);
                return auditLogService.saveAuditLog(null, Action.LOGIN_FAILURE, ipAddress, userAgent)
                        .thenCompose(v -> CompletableFuture.failedFuture(new IpBlockedException()));
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                return auditLogService.saveAuditLog(null, Action.REGISTER_FAILURE, ipAddress, userAgent)
                        .thenCompose(v -> CompletableFuture.failedFuture(new UserAlreadyExistsException()));
            }

            String password = passwordEncoder.encode(request.getPassword());

            User user = User.builder()
                    .email(request.getEmail())
                    .password(password)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User savedUser = userRepository.saveAndFlush(user);
            log.info("User created with email: {}", request.getEmail());

            return roleService.findByName("USER")
                    .thenCompose((Optional<Role> roleOpt) -> {
                        if (roleOpt.isEmpty()) {
                            return auditLogService.saveAuditLog(null, Action.REGISTER_FAILURE, ipAddress, userAgent)
                                    .thenCompose(v -> CompletableFuture
                                            .failedFuture(new RuntimeException("Role not found: USER")));
                        }

                        return roleService.assignRoleToUser(savedUser.getId(), roleOpt.get().getId())
                                .thenCompose(v -> auditLogService.saveAuditLog(savedUser, Action.REGISTER, ipAddress,
                                        userAgent))
                                .thenCompose(v -> passwordHistoryService.savePasswordHistory(savedUser,
                                        savedUser.getPassword()))
                                .thenApply(v -> savedUser);
                    });

        }).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error during user creation: {}", ex.getMessage());
                auditLogService.saveAuditLog(null, Action.REGISTER_FAILURE, ipAddress, userAgent);
            }
        });
    }

    @Async
    public CompletableFuture<TokenResponse> signInWithEmailAndPassword(LoginRequest request, String ipAddress,
            String userAgent) {
        log.info("Attempting to sign in user with email: {}", request.getEmail());

        return ipBlockService.isIpBlocked(ipAddress).thenCompose(isBlocked -> {
            if (isBlocked) {
                log.warn("IP {} is blocked, login attempt denied.", ipAddress);
                return auditLogService.saveAuditLog(null, Action.LOGIN_FAILURE, ipAddress, userAgent)
                        .thenCompose(v -> CompletableFuture.failedFuture(new IpBlockedException()));
            }

            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
            if (optionalUser.isEmpty()) {
                log.warn("User not found with email: {}", request.getEmail());
                return CompletableFuture.allOf(
                        loginEventService.logFailedLoginEvent(null, ipAddress, userAgent, "",
                                ErrorCodes.USER_NOT_FOUND.getTitle()),
                        auditLogService.saveAuditLog(null, Action.LOGIN_FAILURE, ipAddress, userAgent),
                        suspiciousActivityService.logSuspiciousActivity(null, ipAddress, userAgent, "",
                                ErrorCodes.USER_NOT_FOUND.getTitle()),
                        failedLoginAttemptsIpService.handleFailedLogin(ipAddress, ErrorCodes.USER_NOT_FOUND.getTitle()))
                        .thenCompose(v -> CompletableFuture.failedFuture(new UserNotFoundException()));
            }

            User user = optionalUser.get();

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                log.warn("Invalid password for user: {}", request.getEmail());

                return CompletableFuture.allOf(
                        loginEventService.logFailedLoginEvent(user, ipAddress, userAgent, "",
                                ErrorCodes.INVALID_CREDENTIALS.getTitle()),
                        auditLogService.saveAuditLog(user, Action.LOGIN_FAILURE, ipAddress, userAgent),
                        suspiciousActivityService.logSuspiciousActivity(user, ipAddress, userAgent, "",
                                ErrorCodes.INVALID_CREDENTIALS.getTitle()),
                        failedLoginAttemptsIpService.handleFailedLogin(ipAddress,
                                ErrorCodes.INVALID_CREDENTIALS.getTitle()))
                        .thenCompose(v -> CompletableFuture.failedFuture(new InvalidCredentialsException()));
            }

            return tokenService.saveToken(user, ipAddress, userAgent)
                    .thenCompose(tokenResponse -> CompletableFuture.allOf(
                            loginEventService.logSuccessfulLoginEvent(user, ipAddress, userAgent, ""),
                            auditLogService.saveAuditLog(user, Action.LOGIN_SUCCESS, ipAddress, userAgent))
                            .thenApply(v -> {
                                log.info("User signed in successfully: {}", user.getEmail());
                                return tokenResponse;
                            }));
        }).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error during sign-in: {}", ex.getMessage());
                auditLogService.saveAuditLog(null, Action.LOGIN_FAILURE, ipAddress, userAgent);
            }
        });
    }

    public UserResponse getCurrentUser(String ipAddress, String userAgent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotAuthenticatedException();
        }

        User user = (User) authentication.getPrincipal();
        return UserResponse.fromUser(user);
    }

}