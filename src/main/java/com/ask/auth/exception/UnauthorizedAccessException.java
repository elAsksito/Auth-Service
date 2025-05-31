package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends CustomException {

    public UnauthorizedAccessException() {
        super(
            "AUTH-006",
            "Acceso No Autorizado",
            "No tienes permisos suficientes para realizar esta acción.",
            "https://docs.ask.com/errors/AUTH-006",
            HttpStatus.UNAUTHORIZED
        );
    }
}
