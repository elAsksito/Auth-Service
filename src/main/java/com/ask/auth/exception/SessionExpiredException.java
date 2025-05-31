package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class SessionExpiredException extends CustomException {
    public SessionExpiredException() {
        super(
            "AUTH-011",
            "Sesión Expirada",
            "Tu sesión ha expirado. Por favor, inicia sesión de nuevo.",
            "https://docs.ask.com/errors/AUTH-011",
            HttpStatus.UNAUTHORIZED
        );
    }
}