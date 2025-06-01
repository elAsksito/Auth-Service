package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class TooManyLoginAttemptsException extends CustomException {
    public TooManyLoginAttemptsException() {
        super(
            ErrorCodes.TOO_MANY_LOGIN_ATTEMPTS.getCode(),
            ErrorCodes.TOO_MANY_LOGIN_ATTEMPTS.getTitle(),
            ErrorCodes.TOO_MANY_LOGIN_ATTEMPTS.getMessage(),
            ErrorCodes.TOO_MANY_LOGIN_ATTEMPTS.getUri(),
            HttpStatus.TOO_MANY_REQUESTS
        );
    }
}