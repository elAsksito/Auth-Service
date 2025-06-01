package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class InvalidPasswordResetTokenException extends CustomException {
    public InvalidPasswordResetTokenException() {
        super(
            ErrorCodes.INVALID_PASSWORD_RESET_TOKEN.getCode(),
            ErrorCodes.INVALID_PASSWORD_RESET_TOKEN.getTitle(),
            ErrorCodes.INVALID_PASSWORD_RESET_TOKEN.getMessage(),
            ErrorCodes.INVALID_PASSWORD_RESET_TOKEN.getUri(),
            HttpStatus.BAD_REQUEST
        );
    }
}