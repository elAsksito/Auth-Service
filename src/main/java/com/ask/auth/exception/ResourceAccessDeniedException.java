package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class ResourceAccessDeniedException extends CustomException {
    public ResourceAccessDeniedException() {
        super(
            ErrorCodes.RESOURCE_ACCESS_DENIED.getCode(),
            ErrorCodes.RESOURCE_ACCESS_DENIED.getTitle(),
            ErrorCodes.RESOURCE_ACCESS_DENIED.getMessage(),
            ErrorCodes.RESOURCE_ACCESS_DENIED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}