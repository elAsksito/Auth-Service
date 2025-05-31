package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UserCreationFailedException extends CustomException {
    public UserCreationFailedException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.USER_CREATION_FAILED.getCode(),
            messageSource.getMessage(
                ErrorCodes.USER_CREATION_FAILED.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.USER_CREATION_FAILED.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.USER_CREATION_FAILED.getUri(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}