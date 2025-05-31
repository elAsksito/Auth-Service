package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class AccountExpiredException extends CustomException {
    public AccountExpiredException(String email) {
        super(
            "AUTH-014",
            "Cuenta Expirada",
            "La cuenta del usuario '" + email + "' ha expirado.",
            "https://docs.ask.com/errors/AUTH-014",
            HttpStatus.FORBIDDEN
        );
    }
}