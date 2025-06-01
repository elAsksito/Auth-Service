package com.ask.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import com.ask.auth.exception.IpBlockedException;
import com.ask.auth.model.entity.IpBlock;
import com.ask.auth.repository.FailedLoginAttemptsIpRepository;
import com.ask.auth.repository.IpBlockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IpBlockService {

    private final IpBlockRepository ipBlockRepository;
    private final FailedLoginAttemptsIpRepository failedLoginAttemptsRepository;

    public CompletableFuture<Boolean> isIpBlocked(String ipAddress) {
        return CompletableFuture.supplyAsync(() -> {
            return ipBlockRepository.findById(ipAddress)
                    .map(ipBlock -> {
                        Instant now = Instant.now();
                        if (ipBlock.getBlockedUntil() != null && ipBlock.getBlockedUntil().isAfter(now)) {
                            log.info("IP {} sigue bloqueada. Tiempo restante: {} segundos", ipAddress,
                                    ipBlock.getBlockedUntil().getEpochSecond() - now.getEpochSecond());
                            throw new IpBlockedException();
                        } else {
                            ipBlockRepository.deleteById(ipAddress);
                            resetFailedAttempts(ipAddress);
                            log.info("IP {} fue desbloqueada automáticamente", ipAddress);
                            return false;
                        }
                    })
                    .orElse(false);
        });
    }

    public CompletableFuture<IpBlock> blockIp(String ipAddress, int minutes, String reason) {
        Instant now = Instant.now();
        Instant until = now.plus(minutes, ChronoUnit.MINUTES);

        IpBlock ipBlock = IpBlock.builder()
                .ipAddress(ipAddress)
                .blockedUntil(until)
                .reason(reason)
                .createdAt(now)
                .build();

        ipBlockRepository.save(ipBlock);
        log.warn("IP {} bloqueada hasta {} por: {}", ipAddress, until, reason);
        return CompletableFuture.completedFuture(ipBlock);
    }

    public CompletableFuture<IpBlock> unblockIp(String ipAddress) {
        return ipBlockRepository.findById(ipAddress)
                .map(ipBlock -> {
                    ipBlockRepository.deleteById(ipAddress);
                    log.info("IP {} desbloqueada manualmente", ipAddress);
                    failedLoginAttemptsRepository.findById(ipAddress).ifPresent(failedAttempt -> {
                        failedAttempt.setAttempts(0);
                        failedLoginAttemptsRepository.save(failedAttempt);
                        log.info("Intentos fallidos para IP {} reseteados a 0 tras desbloqueo", ipAddress);
                    });

                    return CompletableFuture.completedFuture(ipBlock);
                })
                .orElseGet(() -> {
                    log.info("IP {} no estaba bloqueada", ipAddress);
                    return CompletableFuture.completedFuture(null);
                });
    }

    private void resetFailedAttempts(String ipAddress) {
        failedLoginAttemptsRepository.findById(ipAddress).ifPresent(failedAttempt -> {
            failedAttempt.setAttempts(0);
            failedLoginAttemptsRepository.save(failedAttempt);
            log.info("Intentos fallidos para IP {} reseteados a 0", ipAddress);
        });
    }
}