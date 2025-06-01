package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException() {
        super(
            ErrorCodes.USER_NOT_FOUND.getCode(), 
            ErrorCodes.USER_NOT_FOUND.getTitle(),
            ErrorCodes.USER_NOT_FOUND.getMessage(),
            ErrorCodes.USER_NOT_FOUND.getUri(), 
            HttpStatus.NOT_FOUND
        );
    }
}