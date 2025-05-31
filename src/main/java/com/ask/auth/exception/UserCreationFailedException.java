package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class UserCreationFailedException extends CustomException {
    public UserCreationFailedException(String reason) {
        super(
            "AUTH-015",
            "Error al Crear Usuario",
            "No se pudo crear el usuario debido a: " + reason,
            "https://docs.ask.com/errors/AUTH-015",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}