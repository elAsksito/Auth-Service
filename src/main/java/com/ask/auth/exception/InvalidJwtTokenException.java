package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class InvalidJwtTokenException extends CustomException {

    public InvalidJwtTokenException(String token) {
        super(
            "AUTH-005",
            "Token JWT Inválido",
            "El token JWT proporcionado es inválido o ha expirado.",
            "https://docs.ask.com/errors/AUTH-005",
            HttpStatus.UNAUTHORIZED
        );
    }
}
