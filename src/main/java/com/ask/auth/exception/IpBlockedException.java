package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class IpBlockedException extends CustomException {

    public IpBlockedException() {
        super(
            ErrorCodes.IP_BLOCKED.getCode(), 
            ErrorCodes.IP_BLOCKED.getTitle(),
            ErrorCodes.IP_BLOCKED.getMessage(),
            ErrorCodes.IP_BLOCKED.getUri(), 
            HttpStatus.FORBIDDEN);
    }
}