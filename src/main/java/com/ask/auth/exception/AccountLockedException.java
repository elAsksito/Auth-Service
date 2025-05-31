package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class AccountLockedException extends CustomException {
    public AccountLockedException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.ACCOUNT_LOCKED.getCode(),
            messageSource.getMessage(
                ErrorCodes.ACCOUNT_LOCKED.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.ACCOUNT_LOCKED.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.ACCOUNT_LOCKED.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}