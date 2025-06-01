package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class InvalidCredentialsException extends CustomException {
    public InvalidCredentialsException() {
        super(
            ErrorCodes.INVALID_CREDENTIALS.getCode(),
            ErrorCodes.INVALID_CREDENTIALS.getTitle(),
            ErrorCodes.INVALID_CREDENTIALS.getMessage(),
            ErrorCodes.INVALID_CREDENTIALS.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}