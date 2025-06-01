package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class PasswordAlreadyUsed extends CustomException {
    public PasswordAlreadyUsed() {
        super(
            ErrorCodes.PASSWORD_ALREADY_USED.getCode(),
            ErrorCodes.PASSWORD_ALREADY_USED.getTitle(),
            ErrorCodes.PASSWORD_ALREADY_USED.getMessage(),
            ErrorCodes.PASSWORD_ALREADY_USED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}