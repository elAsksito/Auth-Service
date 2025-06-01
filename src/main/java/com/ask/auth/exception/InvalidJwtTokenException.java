package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class InvalidJwtTokenException extends CustomAuthException {
    public InvalidJwtTokenException() {
        super(
            ErrorCodes.INVALID_JWT_TOKEN.getCode(),
            ErrorCodes.INVALID_JWT_TOKEN.getTitle(),
            ErrorCodes.INVALID_JWT_TOKEN.getMessage(),
            ErrorCodes.INVALID_JWT_TOKEN.getUri(),
            HttpStatus.UNAUTHORIZED
        );
    }
}