package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class AccountExpiredException extends CustomException {
    public AccountExpiredException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.ACCOUNT_EXPIRED.getCode(),
            messageSource.getMessage(
                ErrorCodes.ACCOUNT_EXPIRED.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.ACCOUNT_EXPIRED.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.ACCOUNT_EXPIRED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}