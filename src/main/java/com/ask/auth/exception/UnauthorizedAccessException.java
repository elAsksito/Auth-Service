package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UnauthorizedAccessException extends CustomException {

    public UnauthorizedAccessException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.UNAUTHORIZED_ACCESS.getCode(),
            messageSource.getMessage(
                ErrorCodes.UNAUTHORIZED_ACCESS.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.UNAUTHORIZED_ACCESS.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.UNAUTHORIZED_ACCESS.getUri(),
            HttpStatus.UNAUTHORIZED
        );
    }
}