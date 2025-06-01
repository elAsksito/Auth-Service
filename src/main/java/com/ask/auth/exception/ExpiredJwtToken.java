package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class ExpiredJwtToken extends CustomAuthException{
    public ExpiredJwtToken() {
        super(
            ErrorCodes.EXPIRED_JWT_TOKEN.getCode(),
            ErrorCodes.EXPIRED_JWT_TOKEN.getTitle(),
            ErrorCodes.EXPIRED_JWT_TOKEN.getMessage(),
            ErrorCodes.EXPIRED_JWT_TOKEN.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}