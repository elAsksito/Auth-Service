package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class WeakPasswordException extends CustomException {
    public WeakPasswordException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.WEAK_PASSWORD.getCode(),
            messageSource.getMessage(
                ErrorCodes.WEAK_PASSWORD.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.WEAK_PASSWORD.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.WEAK_PASSWORD.getUri(),
            HttpStatus.BAD_REQUEST
        );
    }
}