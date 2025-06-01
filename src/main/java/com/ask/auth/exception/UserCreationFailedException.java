package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UserCreationFailedException extends CustomException {
    public UserCreationFailedException() {
        super(
            ErrorCodes.USER_CREATION_FAILED.getCode(),
            ErrorCodes.USER_CREATION_FAILED.getTitle(),
            ErrorCodes.USER_CREATION_FAILED.getMessage(),
            ErrorCodes.USER_CREATION_FAILED.getUri(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}