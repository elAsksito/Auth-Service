package com.ask.auth.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ask.auth.exception.PasswordAlreadyUsed;
import com.ask.auth.model.entity.PasswordHistory;
import com.ask.auth.model.entity.User;
import com.ask.auth.repository.PasswordHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordHistoryService {
    private final PasswordHistoryRepository passwordHistoryRepository;

    @Async
    @Transactional
    public CompletableFuture<PasswordHistory> savePasswordHistory(User user, String password) {

        if (passwordAlreadyUsed(user, password)) {
            log.warn("Password already used for user: {}", user.getEmail());
            throw new PasswordAlreadyUsed();
        }

        PasswordHistory savedHistory = PasswordHistory.builder()
                .user(user)
                .passwordHash(password)
                .build();
        return CompletableFuture.completedFuture(passwordHistoryRepository.save(savedHistory));
    }

    private boolean passwordAlreadyUsed(User user, String passwordHash) {
        return passwordHistoryRepository.existsByUserAndPasswordHash(user, passwordHash);
    }
}