package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class IpBlockedException extends CustomException {

    public IpBlockedException(String ipAddress) {
        super(
            "AUTH-004", 
            "IP Bloqueada", 
            "La dirección IP " + ipAddress + " ha sido bloqueada por múltiples intentos fallidos de inicio de sesión.", 
            "https://docs.ask.com/errors/AUTH-004", 
            HttpStatus.FORBIDDEN);
    }
}