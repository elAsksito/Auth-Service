package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException{

    public UserNotFoundException(String email) {
        super("AUTH-001", 
        "Usuario no encontrado", 
        "El usuario " + email + " no existe en el sistema.", 
        "https://docs.ask.com/errors/AUTH-001", HttpStatus.NOT_FOUND);
    }
    
}