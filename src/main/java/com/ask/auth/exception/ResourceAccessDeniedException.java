package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class ResourceAccessDeniedException extends CustomException {
    public ResourceAccessDeniedException(String resource) {
        super(
            "AUTH-013",
            "Acceso Denegado al Recurso",
            "No tienes permisos para acceder al recurso: " + resource,
            "https://docs.ask.com/errors/AUTH-013",
            HttpStatus.FORBIDDEN
        );
    }
}