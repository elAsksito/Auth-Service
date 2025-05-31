package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class EmailNotVerifiedException extends CustomException {
    public EmailNotVerifiedException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.EMAIL_NOT_VERIFIED.getCode(),
            messageSource.getMessage(
                ErrorCodes.EMAIL_NOT_VERIFIED.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.EMAIL_NOT_VERIFIED.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.EMAIL_NOT_VERIFIED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}