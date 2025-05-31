package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class TooManyLoginAttemptsException extends CustomException {
    public TooManyLoginAttemptsException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.TOO_MANY_LOGIN_ATTEMPTS.getCode(),
            messageSource.getMessage(
                ErrorCodes.TOO_MANY_LOGIN_ATTEMPTS.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.TOO_MANY_LOGIN_ATTEMPTS.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.TOO_MANY_LOGIN_ATTEMPTS.getUri(),
            HttpStatus.TOO_MANY_REQUESTS
        );
    }
}