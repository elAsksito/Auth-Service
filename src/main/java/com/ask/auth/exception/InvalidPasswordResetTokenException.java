package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class InvalidPasswordResetTokenException extends CustomException {
    public InvalidPasswordResetTokenException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.INVALID_PASSWORD_RESET_TOKEN.getCode(),
            messageSource.getMessage(
                ErrorCodes.INVALID_PASSWORD_RESET_TOKEN.getTitleKey(),
                null,
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.INVALID_PASSWORD_RESET_TOKEN.getDescriptionKey(),
                null,
                locale
            ),
            ErrorCodes.INVALID_PASSWORD_RESET_TOKEN.getUri(),
            HttpStatus.BAD_REQUEST
        );
    }
}