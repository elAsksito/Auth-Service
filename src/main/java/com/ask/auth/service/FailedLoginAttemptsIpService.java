package com.ask.auth.service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ask.auth.exception.TooManyLoginAttemptsException;
import com.ask.auth.model.entity.FailedLoginAttemptsIp;
import com.ask.auth.repository.FailedLoginAttemptsIpRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FailedLoginAttemptsIpService {

    private final FailedLoginAttemptsIpRepository failedLoginAttemptsRepository;
    private final IpBlockService ipBlockService;

    private static final int MAX_ATTEMPTS = 5;

    @Async
    public CompletableFuture<FailedLoginAttemptsIp> handleFailedLogin(String ipAddress, String reason) {

        FailedLoginAttemptsIp updatedOrNewAttempt = failedLoginAttemptsRepository.findById(ipAddress)
                .map(existing -> {

                    boolean isBlocked = existing.getBlockedUntil() != null
                            && existing.getBlockedUntil().isAfter(Instant.now());

                    if (isBlocked) {
                        log.warn("Intento fallido desde IP bloqueada: {}", ipAddress);
                        throw new TooManyLoginAttemptsException();
                    } else {
                        if (existing.getAttempts() == 0 || existing.getBlockedUntil() != null) {
                            existing.setAttempts(1);
                        } else {
                            existing.setAttempts(existing.getAttempts() + 1);
                        }

                        existing.setBlockedUntil(null);
                        existing.setLastAttempt(Instant.now());
                    }

                    FailedLoginAttemptsIp saved = failedLoginAttemptsRepository.save(existing);

                    if (saved.getAttempts() >= MAX_ATTEMPTS) {
                        ipBlockService.blockIp(ipAddress, 1, reason);
                        log.warn("La IP {} ha sido bloqueada por exceder los intentos fallidos ({})", ipAddress,
                                saved.getAttempts());
                        throw new TooManyLoginAttemptsException();
                    }

                    return saved;
                })
                .orElseGet(() -> {
                    FailedLoginAttemptsIp newAttempt = FailedLoginAttemptsIp.builder()
                            .ipAddress(ipAddress)
                            .attempts(1)
                            .lastAttempt(Instant.now())
                            .build();
                    return failedLoginAttemptsRepository.save(newAttempt);
                });

        return CompletableFuture.completedFuture(updatedOrNewAttempt);
    }

    public CompletableFuture<FailedLoginAttemptsIp> incrementFailedAttempts(String ipAddress) {
        FailedLoginAttemptsIp updatedOrNewAttempt = failedLoginAttemptsRepository.findById(ipAddress)
                .map(existing -> {
                    existing.setAttempts(existing.getAttempts() + 1);
                    return failedLoginAttemptsRepository.save(existing);
                })
                .orElseGet(() -> {
                    FailedLoginAttemptsIp newAttempt = FailedLoginAttemptsIp.builder()
                            .ipAddress(ipAddress)
                            .attempts(1)
                            .build();
                    return failedLoginAttemptsRepository.save(newAttempt);
                });

        return CompletableFuture.completedFuture(updatedOrNewAttempt);
    }
}