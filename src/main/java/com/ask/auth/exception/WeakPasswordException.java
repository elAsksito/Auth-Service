package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class WeakPasswordException extends CustomException {
    public WeakPasswordException() {
        super(
            ErrorCodes.WEAK_PASSWORD.getCode(),
            ErrorCodes.WEAK_PASSWORD.getTitle(),
            ErrorCodes.WEAK_PASSWORD.getMessage(),
            ErrorCodes.WEAK_PASSWORD.getUri(),
            HttpStatus.BAD_REQUEST
        );
    }
}