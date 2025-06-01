package com.ask.auth.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.ask.auth.model.entity.SuspiciousActivity;
import com.ask.auth.model.entity.User;
import com.ask.auth.repository.SuspiciousActivityRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuspiciousActivityService {
    

    private final SuspiciousActivityRepository suspiciousActivityRepository;

    public CompletableFuture<SuspiciousActivity> logSuspiciousActivity(
            User user,
            String ipAddress,
            String userAgent,
            String fingerprint,
            String reason) {

        String email = user != null ? user.getEmail() : "N/A";
        String userId = user != null && user.getId() != null ? user.getId().toString() : "N/A";

        String metadata = String.format(
            "{\"email\": \"%s\", \"userId\": \"%s\", \"ipAddress\": \"%s\", \"userAgent\": \"%s\", \"timestamp\": \"%d\"}",
            email, userId, ipAddress, userAgent, System.currentTimeMillis()
        );

        SuspiciousActivity activity = SuspiciousActivity.builder()
                .user(user)
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .deviceFingerprint(fingerprint)
                .reason(reason)
                .details(metadata)
                .build();

        SuspiciousActivity savedActivity = suspiciousActivityRepository.save(activity);
        log.info("Actividad sospechosa registrada para el usuario: {}", user.getEmail());
        return CompletableFuture.completedFuture(savedActivity);
    }
}