package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends CustomException {

    public UserAlreadyExistsException(String email) {
        super(
            "AUTH-008",
            "Usuario ya existe",
            "El usuario con el correo electrónico " + email + " ya está registrado.",
            "https://docs.ask.com/errors/AUTH-008",
            HttpStatus.CONFLICT
        );
    }
    
}
