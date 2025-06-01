package com.ask.auth.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ask.auth.exception.ExpiredJwtToken;
import com.ask.auth.exception.InvalidJwtTokenException;
import com.ask.auth.model.entity.User;
import com.ask.auth.repository.UserRepository;
import com.ask.auth.security.entrypoint.JwtAuthenticationEntryPoint;
import com.ask.auth.security.jwt.JwtService;
import com.ask.auth.service.SessionService;
import com.ask.auth.service.TokenService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final SessionService sessionService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            authenticationEntryPoint.commence(request, response,
                    new InvalidJwtTokenException());
        }

        Claims claims = jwtService.extractAllClaims(token);
        String userId = claims.getSubject();

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            authenticationEntryPoint.commence(request, response,
                    new InvalidJwtTokenException());
        }

        boolean isTokenActive = tokenService.isTokenActive(token);

        boolean isSessionActive = sessionService.isSessionActive(userId);

        if (isTokenActive && isSessionActive) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user, null, null);
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            authenticationEntryPoint.commence(request, response,
                    new ExpiredJwtToken());
        }

        filterChain.doFilter(request, response);
    }

}