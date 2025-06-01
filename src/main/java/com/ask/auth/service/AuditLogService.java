package com.ask.auth.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ask.auth.model.entity.AuditLog;
import com.ask.auth.model.entity.User;
import com.ask.auth.model.enums.Action;
import com.ask.auth.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Async
    @Transactional
    public CompletableFuture<AuditLog> saveAuditLog(
        User user, Action action, String ipAddress, String userAgent) {

        String email = user != null ? user.getEmail() : "N/A";
        String userId = user != null && user.getId() != null ? user.getId().toString() : "N/A";

        String metadata = String.format(
            "{\"email\": \"%s\", \"userId\": \"%s\", \"action\": \"%s\", \"ipAddress\": \"%s\", \"userAgent\": \"%s\", \"timestamp\": \"%d\"}",
            email, userId, action, ipAddress, userAgent, System.currentTimeMillis()
        );

        AuditLog auditLog = AuditLog.builder()
            .user(user)
            .action(action)
            .metadata(metadata)
            .ipAddress(ipAddress)
            .userAgent(userAgent)
            .build();

        AuditLog saved = auditLogRepository.save(auditLog);
        
        return CompletableFuture.completedFuture(saved);
    }

    
    @Async
    public CompletableFuture<List<AuditLog>> findAll() {
        List<AuditLog> all = auditLogRepository.findAll();
        return CompletableFuture.completedFuture(all);
    }

    @Async
    public CompletableFuture<List<AuditLog>> findByUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            log.warn("findByUserIdAsync called with null or empty userId");
            return CompletableFuture.completedFuture(List.of());
        }
        List<AuditLog> logs = auditLogRepository.findByUserId(userId);
        return CompletableFuture.completedFuture(logs);
    }
}