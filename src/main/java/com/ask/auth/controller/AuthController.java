package com.ask.auth.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ask.auth.model.entity.User;
import com.ask.auth.payload.request.LoginRequest;
import com.ask.auth.payload.response.TokenResponse;
import com.ask.auth.payload.response.UserResponse;
import com.ask.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<User>> registerUser(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        String ipAddress = extractClientIp(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        return authService.createUserWithEmailAndPassword(request, ipAddress, userAgent)
                .thenApply(user -> ResponseEntity.ok(user));
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<TokenResponse>> loginUser(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        String ipAddress = extractClientIp(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        return authService.signInWithEmailAndPassword(request, ipAddress, userAgent)
                .thenApply(user -> ResponseEntity.ok(user));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(HttpServletRequest httpRequest) {
        String ipAddress = extractClientIp(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        UserResponse userResponse = authService.getCurrentUser(ipAddress, userAgent);
        return ResponseEntity.ok(userResponse);
    }

    private String extractClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}