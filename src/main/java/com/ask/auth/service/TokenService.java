package com.ask.auth.service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ask.auth.model.entity.Token;
import com.ask.auth.model.entity.User;
import com.ask.auth.model.enums.TokenType;
import com.ask.auth.payload.response.TokenResponse;
import com.ask.auth.repository.TokenRepository;
import com.ask.auth.security.jwt.JwtService;
import com.ask.auth.utils.HashUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Async
    @Transactional
    public CompletableFuture<TokenResponse> saveToken(User user, String ipAddress, String userAgent) {
        try {
            String accessToken = jwtService.generateAccessToken(user, ipAddress, userAgent);
            String refreshToken = jwtService.generateRefreshToken(user);

            byte[] hashedAccessToken = hashToken(accessToken);
            byte[] hashedRefreshToken = hashToken(refreshToken);

            Token accessTokenEntity = Token.builder()
                    .user(user)
                    .tokenHash(hashedAccessToken)
                    .tokenType(TokenType.ACCESS)
                    .userAgent(userAgent)
                    .ipAddress(ipAddress)
                    .isRevoked(false)
                    .expiresAt(Instant.now().plusSeconds(7 * 24 * 3600))
                    .build();

            Token refreshTokenEntity = Token.builder()
                    .user(user)
                    .tokenHash(hashedRefreshToken)
                    .tokenType(TokenType.REFRESH)
                    .userAgent(userAgent)
                    .ipAddress(ipAddress)
                    .isRevoked(false)
                    .expiresAt(Instant.now().plusSeconds(24 * 3600))
                    .build();

            tokenRepository.save(accessTokenEntity);
            tokenRepository.save(refreshTokenEntity);

            TokenResponse tokenResponse = new TokenResponse();
            tokenResponse.setAccessToken(accessToken);
            tokenResponse.setRefreshToken(refreshToken);

            return CompletableFuture.completedFuture(tokenResponse);
        } catch (Exception e) {
            log.error("Error al generar el token: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudo generar el token", e);
        }
    }

    public boolean isTokenActive(String plainToken) {
        byte[] hashedToken = hashToken(plainToken);
        return tokenRepository.findByTokenHash(hashedToken)
            .filter(t -> !t.isRevoked() && t.getExpiresAt().isAfter(Instant.now()))
            .isPresent();
    }

    private byte[] hashToken(String token) {
        return HashUtil.hashToken(token);
    }
}