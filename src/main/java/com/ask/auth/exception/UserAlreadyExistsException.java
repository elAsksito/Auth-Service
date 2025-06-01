package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException() {
        super(
            ErrorCodes.USER_ALREADY_EXISTS.getCode(),
            ErrorCodes.USER_ALREADY_EXISTS.getTitle(),
            ErrorCodes.USER_ALREADY_EXISTS.getMessage(),
            ErrorCodes.USER_ALREADY_EXISTS.getUri(),
            HttpStatus.CONFLICT
        );
    }
}