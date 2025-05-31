package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordResetTokenException extends CustomException {
    public InvalidPasswordResetTokenException() {
        super(
            "AUTH-010",
            "Token de Restablecimiento Inválido",
            "El token para restablecer la contraseña es inválido o ha expirado.",
            "https://docs.ask.com/errors/AUTH-010",
            HttpStatus.BAD_REQUEST
        );
    }
}