package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class AccountLockedException extends CustomException {
    public AccountLockedException(String email) {
        super(
            "AUTH-003",
            "Cuenta Bloqueada",
            "La cuenta del usuario '" + email + "' está bloqueada temporalmente.",
            "https://docs.ask.com/errors/AUTH-003",
            HttpStatus.FORBIDDEN
        );
    }
}