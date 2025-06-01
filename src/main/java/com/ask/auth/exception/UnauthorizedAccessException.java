package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UnauthorizedAccessException extends CustomException {
    public UnauthorizedAccessException() {
        super(
            ErrorCodes.UNAUTHORIZED_ACCESS.getCode(),
            ErrorCodes.UNAUTHORIZED_ACCESS.getTitle(),
            ErrorCodes.UNAUTHORIZED_ACCESS.getMessage(),
            ErrorCodes.UNAUTHORIZED_ACCESS.getUri(),
            HttpStatus.UNAUTHORIZED
        );
    }
}