package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UserInactiveException extends CustomException {
    public UserInactiveException() {
        super(
            ErrorCodes.USER_INACTIVE.getCode(),
            ErrorCodes.USER_INACTIVE.getTitle(),
            ErrorCodes.USER_INACTIVE.getMessage(),
            ErrorCodes.USER_INACTIVE.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}