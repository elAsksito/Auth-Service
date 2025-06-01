package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class AccountLockedException extends CustomException {
    public AccountLockedException() {
        super(
            ErrorCodes.ACCOUNT_LOCKED.getCode(),
            ErrorCodes.ACCOUNT_LOCKED.getTitle(),
            ErrorCodes.ACCOUNT_LOCKED.getMessage(),
            ErrorCodes.ACCOUNT_LOCKED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}