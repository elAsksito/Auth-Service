package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class AccountExpiredException extends CustomException {
    public AccountExpiredException() {
        super(
            ErrorCodes.ACCOUNT_EXPIRED.getCode(),
            ErrorCodes.ACCOUNT_EXPIRED.getTitle(),
            ErrorCodes.ACCOUNT_EXPIRED.getMessage(),
            ErrorCodes.ACCOUNT_EXPIRED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}