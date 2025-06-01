package com.ask.auth.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ask.auth.model.entity.User;
import com.ask.auth.payload.request.LoginRequest;
import com.ask.auth.payload.response.TokenResponse;
import com.ask.auth.payload.response.UserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    @Async
    public CompletableFuture<User> createUserWithEmailAndPassword(LoginRequest request, String ipAddress,
            String userAgent) {
        CompletableFuture<User> user = userService.createUserWithEmailAndPassword(request, ipAddress, userAgent);
        return user;
    }

    @Async
    public CompletableFuture<TokenResponse> signInWithEmailAndPassword(LoginRequest request, String ipAddress,
            String userAgent) {
        CompletableFuture<TokenResponse> tokenResponse = userService.signInWithEmailAndPassword(request, ipAddress,
                userAgent);
        return tokenResponse;
    }

    public UserResponse getCurrentUser(String ipAddress, String userAgent) {
        return userService.getCurrentUser(ipAddress, userAgent);
    }
}