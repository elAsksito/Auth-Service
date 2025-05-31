package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.INVALID_PASSWORD.getCode(), 
            messageSource.getMessage(
                ErrorCodes.INVALID_PASSWORD.getTitleKey(), 
                null, 
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.INVALID_PASSWORD.getDescriptionKey(), 
                null, 
                locale
            ),
            ErrorCodes.INVALID_PASSWORD.getUri(), 
            HttpStatus.UNAUTHORIZED
        );
    }
}