package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends CustomException {

    public InvalidPasswordException(String email) {
        super(
            "AUTH-002", 
            "Contraseña inválida", 
            "La contraseña proporcionada para el usuario " + email + " es incorrecta.", 
            "https://docs.ask.com/errors/AUTH-002", 
            HttpStatus.UNAUTHORIZED);
    }
}