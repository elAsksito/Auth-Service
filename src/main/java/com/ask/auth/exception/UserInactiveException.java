package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UserInactiveException extends CustomException {
    public UserInactiveException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.USER_INACTIVE.getCode(),
            messageSource.getMessage(
                ErrorCodes.USER_INACTIVE.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.USER_INACTIVE.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.USER_INACTIVE.getUri(),
            HttpStatus.FORBIDDEN
        );
    }
}