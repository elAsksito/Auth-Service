package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class UserInactiveException extends CustomException {
    public UserInactiveException(String email) {
        super(
            "AUTH-012",
            "Usuario Inactivo",
            "El usuario '" + email + "' está inactivo. Contacta al administrador.",
            "https://docs.ask.com/errors/AUTH-012",
            HttpStatus.FORBIDDEN
        );
    }
}