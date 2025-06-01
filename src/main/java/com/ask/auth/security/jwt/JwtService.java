package com.ask.auth.security.jwt;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Instant;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ask.auth.exception.InvalidJwtTokenException;
import com.ask.auth.model.entity.User;
import com.ask.auth.model.entity.UserRole;
import com.ask.auth.security.utils.TokenExpiration;
import com.ask.auth.service.RoleService;
import com.ask.auth.service.SessionService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor 
public class JwtService {

    private final SessionService sessionService;
    private final RoleService roleService;
    private final PrivateKey jwtPrivateKey;
    private final PublicKey jwtPublicKey;

    public String generateAccessToken(User user, String ipAddress, String userAgent) {
        List<UserRole> userRoles;
        List<String> roleNames;
 
        try {
            userRoles = roleService.getUserRoles(user.getId());
            roleNames = userRoles.stream()
                    .map(ur -> ur.getRole() != null ? ur.getRole().getName() : null)
                    .filter(name -> name != null)
                    .toList();
        } catch (ConcurrentModificationException e) {
            log.error("Concurrent modification while mapping userRoles", e);
            roleNames = List.of();
        }

        String sessionId = UUID.randomUUID().toString();

        String token = Jwts.builder()
                .subject(user.getId())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusSeconds(TokenExpiration.ACCESS.getSeconds())))
                .claims(Map.of(
                        "email", user.getEmail(),
                        "roles", roleNames,
                        "sessionId", sessionId))
                .signWith(jwtPrivateKey)
                .compact();

        sessionService.createSession(sessionId, user, token, ipAddress, userAgent);

        return token;
    }

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getId())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusSeconds(TokenExpiration.REFRESH.getSeconds())))
                .signWith(jwtPrivateKey)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);

            String sessionId = claims.get("sessionId", String.class);
            if (sessionId == null) {
                log.warn("Token no tiene sessionId");
                throw new InvalidJwtTokenException();
            }

            if (!sessionService.isSessionValid(sessionId, token)) {
                throw new InvalidJwtTokenException();
            }

            return true;
        } catch (InvalidJwtTokenException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Error validando token JWT: {}", e.getMessage());
            return false;
        }
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtPublicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}