package com.ask.auth.exception;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Error> handleCustomException(CustomException ex) {
        Error error = Error.builder()
                .errorCode(ex.getErrorCode())
                .errorTitle(ex.getErrorTitle())
                .errorDescription(ex.getErrorDescription())
                .errorUri(ex.getErrorUri())
                .errorType(ex.getClass().getSimpleName())
                .errorStatus(ex.getHttpStatus().toString())
                .errorTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .httpStatus(ex.getHttpStatus().value())
                .build();

        return new ResponseEntity<>(error, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleGenericException(Exception ex) {
        Error error = Error.builder()
                .errorCode("GEN-000")
                .errorTitle("Error Interno del Servidor")
                .errorDescription("Ha ocurrido un error inesperado.")
                .errorUri("https://docs.ask.com/errors/GEN-000")
                .errorType(ex.getClass().getSimpleName())
                .errorStatus("500 INTERNAL_SERVER_ERROR")
                .errorTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .httpStatus(500)
                .build();

        return ResponseEntity.status(500).body(error);
    }
    
}