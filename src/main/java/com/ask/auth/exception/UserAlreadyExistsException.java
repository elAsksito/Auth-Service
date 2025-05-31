package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.USER_ALREADY_EXISTS.getCode(),
            messageSource.getMessage(
                ErrorCodes.USER_ALREADY_EXISTS.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.USER_ALREADY_EXISTS.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.USER_ALREADY_EXISTS.getUri(),
            HttpStatus.CONFLICT
        );
    }
}