package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class ResourceAccessDeniedException extends CustomException {
    public ResourceAccessDeniedException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.RESOURCE_ACCESS_DENIED.getCode(),
            messageSource.getMessage(
                ErrorCodes.RESOURCE_ACCESS_DENIED.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.RESOURCE_ACCESS_DENIED.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.RESOURCE_ACCESS_DENIED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}