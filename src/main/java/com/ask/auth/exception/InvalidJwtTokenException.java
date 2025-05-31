package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class InvalidJwtTokenException extends CustomException {
    public InvalidJwtTokenException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.INVALID_JWT_TOKEN.getCode(),
            messageSource.getMessage(
                ErrorCodes.INVALID_JWT_TOKEN.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.INVALID_JWT_TOKEN.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.INVALID_JWT_TOKEN.getUri(),
            HttpStatus.UNAUTHORIZED
        );
    }
}