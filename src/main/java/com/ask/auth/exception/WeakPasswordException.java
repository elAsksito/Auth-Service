package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class WeakPasswordException extends CustomException {
    public WeakPasswordException() {
        super(
            "AUTH-016",
            "Contraseña Débil",
            "La contraseña no cumple con los requisitos mínimos de seguridad.",
            "https://docs.ask.com/errors/AUTH-016",
            HttpStatus.BAD_REQUEST
        );
    }
}