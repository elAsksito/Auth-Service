package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class IpBlockedException extends CustomException {

    public IpBlockedException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.IP_BLOCKED.getCode(), 
            messageSource.getMessage(
                ErrorCodes.IP_BLOCKED.getTitleKey(), 
                null, 
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.IP_BLOCKED.getDescriptionKey(), 
                null, 
                locale
            ),
            ErrorCodes.IP_BLOCKED.getUri(), 
            HttpStatus.FORBIDDEN);
    }
}