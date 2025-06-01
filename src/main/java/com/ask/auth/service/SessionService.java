package com.ask.auth.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ask.auth.model.entity.Session;
import com.ask.auth.model.entity.User;
import com.ask.auth.repository.SessionRepository;
import com.ask.auth.security.utils.TokenExpiration;
import com.ask.auth.utils.HashUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    @Async
    @Transactional
    public CompletableFuture<Void> createSession(String sessionId, User user, String accessToken, String ipAddress,
            String userAgent) {
        return CompletableFuture.runAsync(() -> {
            try {
                byte[] tokenHash = hashToken(accessToken);

                Session session = Session.builder()
                        .id(sessionId)
                        .user(user)
                        .ipAddress(ipAddress)
                        .userAgent(userAgent)
                        .sessionTokenHash(tokenHash)
                        .createdAt(Instant.now())
                        .expiresAt(Instant.now().plusSeconds(TokenExpiration.SESSION.getSeconds()))
                        .lastActivity(Instant.now())
                        .build();

                sessionRepository.save(session);
                log.info("Session created for user: {} from IP: {}", user.getEmail(), ipAddress);
            } catch (Exception e) {
                log.error("Error creating session: {}", e.getMessage(), e);
                throw new RuntimeException("Error creating session", e);
            }
        });
    }

    public boolean isSessionActive(String userId) {
        return sessionRepository.findByUserId(userId)
                .filter(s -> s.getExpiresAt().isAfter(Instant.now()))
                .isPresent();
    }

    @Async
    @Transactional
    public CompletableFuture<Void> revokeSessionById(String sessionId) {
        return CompletableFuture.runAsync(() -> {
            Optional<Session> sessionOpt = sessionRepository.findById(sessionId);
            if (sessionOpt.isPresent()) {
                Session session = sessionOpt.get();
                if (!session.isRevoked()) {
                    session.setRevoked(true);
                    session.setRevokedAt(Instant.now());
                    sessionRepository.save(session);
                    log.info("Session {} revoked", sessionId);
                }
            } else {
                log.warn("Session {} not found for revocation", sessionId);
            }
        });
    }

    @Async
    @Transactional
    public CompletableFuture<Void> revokeAllSessionsByUser(User user) {
        return CompletableFuture.runAsync(() -> {
            List<Session> activeSessions = sessionRepository.findByUserAndIsRevokedFalse(user);
            for (Session session : activeSessions) {
                session.setRevoked(true);
                session.setRevokedAt(Instant.now());
            }
            sessionRepository.saveAll(activeSessions);
            log.info("Revoked all active sessions for user {}", user.getEmail());
        });
    }

    @Async
    @Transactional
    public CompletableFuture<Void> updateLastActivity(String sessionId) {
        return CompletableFuture.runAsync(() -> {
            sessionRepository.findById(sessionId).ifPresent(session -> {
                session.setLastActivity(Instant.now());
                sessionRepository.save(session);
                log.debug("Updated last activity for session {}", sessionId);
            });
        });
    }

    public boolean isSessionValid(String sessionId, String token) {
        try {
            Session session = sessionRepository.findById(sessionId).orElse(null);
            if (session == null || session.isRevoked()) {
                return false;
            }

            if (session.getExpiresAt().isBefore(Instant.now())) {
                return false;
            }

            byte[] tokenHash = MessageDigest.getInstance("SHA-256").digest(token.getBytes());
            return MessageDigest.isEqual(tokenHash, session.getSessionTokenHash());

        } catch (NoSuchAlgorithmException e) {
            log.error("Error hashing token during session validation", e);
            return false;
        }
    }

    private byte[] hashToken(String token) {
        return HashUtil.hashToken(token);
    }
}