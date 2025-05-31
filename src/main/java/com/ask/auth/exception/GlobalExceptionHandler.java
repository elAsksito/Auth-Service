package com.ask.auth.exception;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ask.auth.payload.ApiError;
import com.ask.auth.payload.ApiResponse;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCustomException(CustomException ex) {
        ApiError error = buildApiError(
                ex.getErrorCode(),
                ex.getErrorTitle(),
                ex.getErrorDescription(),
                ex.getErrorUri(),
                ex.getHttpStatus(),
                ex.getClass().getSimpleName(),
                null
        );

        return buildErrorResponse(error, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1 + "; " + msg2
                ));

        ApiError error = buildApiError(
                "VALIDATION_ERROR",
                "Error de Validación",
                "Uno o más campos tienen errores de validación.",
                "https://docs.ask.com/errors/VALIDATION_ERROR",
                HttpStatus.BAD_REQUEST,
                ex.getClass().getSimpleName(),
                validationErrors
        );

        return buildErrorResponse(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGenericException(Exception ex) {
        ApiError error = buildApiError(
                "GENERIC_ERROR",
                "Error Interno del Servidor",
                "Ha ocurrido un error inesperado.",
                "https://docs.ask.com/errors/GENERIC_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getClass().getSimpleName(),
                null
        );

        return buildErrorResponse(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ApiError buildApiError(
            String code,
            String title,
            String description,
            String uri,
            HttpStatus status,
            String type,
            Map<String, String> extraData
    ) {
        return ApiError.builder()
                .errorCode(code)
                .errorTitle(title)
                .errorDescription(description)
                .errorUri(uri)
                .errorType(type)
                .errorStatus(status.toString())
                .errorTimestamp(ZonedDateTime.now().format(FORMATTER))
                .httpStatus(status.value())
                .extraData(extraData)
                .build();
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponse(ApiError error, HttpStatus status) {
        ApiResponse<?> response = ApiResponse.builder()
                .success(false)
                .error(error)
                .timestamp(ZonedDateTime.now())
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
