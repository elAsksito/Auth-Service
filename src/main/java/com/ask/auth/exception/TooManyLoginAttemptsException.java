package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class TooManyLoginAttemptsException extends CustomException {

    public TooManyLoginAttemptsException() {
        super(
            "AUTH-007",
            "Demasiados Intentos de Inicio de Sesión",
            "Has superado el número máximo de intentos de inicio de sesión permitidos. Por favor, inténtalo de nuevo más tarde.",
            "https://docs.ask.com/errors/AUTH-007",
            HttpStatus.TOO_MANY_REQUESTS
        );
    }
}
