package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class NotAuthenticatedException extends CustomException {
    public NotAuthenticatedException() {
        super(
            ErrorCodes.NOT_AUTHENTICATED.getCode(),
            ErrorCodes.NOT_AUTHENTICATED.getTitle(),
            ErrorCodes.NOT_AUTHENTICATED.getMessage(),
            ErrorCodes.NOT_AUTHENTICATED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}