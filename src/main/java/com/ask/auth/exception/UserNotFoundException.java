package com.ask.auth.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.ask.auth.payload.ErrorCodes;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException(MessageSource messageSource, Locale locale) {
        super(
            ErrorCodes.USER_NOT_FOUND.getCode(), 
            messageSource.getMessage(
                ErrorCodes.USER_NOT_FOUND.getTitleKey(), 
                null, 
                locale
            ),
            messageSource.getMessage(
                ErrorCodes.USER_NOT_FOUND.getDescriptionKey(), 
                null, 
                locale
            ),
            ErrorCodes.USER_NOT_FOUND.getUri(), 
            HttpStatus.NOT_FOUND
        );
    }
}