package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class SessionExpiredException extends CustomException {
    public SessionExpiredException() {
        super(
            ErrorCodes.SESSION_EXPIRED.getCode(),
            ErrorCodes.SESSION_EXPIRED.getTitle(),
            ErrorCodes.SESSION_EXPIRED.getMessage(),
            ErrorCodes.SESSION_EXPIRED.getUri(),
            HttpStatus.UNAUTHORIZED
        );
    }
}