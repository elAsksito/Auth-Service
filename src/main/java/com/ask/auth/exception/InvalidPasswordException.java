package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException() {
        super(
            ErrorCodes.INVALID_PASSWORD.getCode(), 
            ErrorCodes.INVALID_PASSWORD.getTitle(),
            ErrorCodes.INVALID_PASSWORD.getMessage(),
            ErrorCodes.INVALID_PASSWORD.getUri(), 
            HttpStatus.UNAUTHORIZED
        );
    }
}