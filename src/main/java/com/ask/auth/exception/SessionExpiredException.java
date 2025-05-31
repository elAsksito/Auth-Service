package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class SessionExpiredException extends CustomException {
    public SessionExpiredException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.SESSION_EXPIRED.getCode(),
            messageSource.getMessage(
                ErrorCodes.SESSION_EXPIRED.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.SESSION_EXPIRED.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.SESSION_EXPIRED.getUri(),
            HttpStatus.UNAUTHORIZED
        );
    }
}