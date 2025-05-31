package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class EmailNotVerifiedException extends CustomException {
    public EmailNotVerifiedException(String email) {
        super(
            "AUTH-009",
            "Email No Verificado",
            "El email '" + email + "' no ha sido verificado.",
            "https://docs.ask.com/errors/AUTH-009",
            HttpStatus.FORBIDDEN
        );
    }
}