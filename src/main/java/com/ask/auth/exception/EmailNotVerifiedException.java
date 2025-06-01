package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class EmailNotVerifiedException extends CustomException {
    public EmailNotVerifiedException() {
        super(
            ErrorCodes.EMAIL_NOT_VERIFIED.getCode(),
            ErrorCodes.EMAIL_NOT_VERIFIED.getTitle(),
            ErrorCodes.EMAIL_NOT_VERIFIED.getMessage(),
            ErrorCodes.EMAIL_NOT_VERIFIED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}