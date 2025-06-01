package com.ask.auth.security.entrypoint;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.ask.auth.exception.CustomAuthException;
import com.ask.auth.exception.CustomException;
import com.ask.auth.payload.ErrorCodes;
import com.ask.auth.payload.response.ApiError;
import com.ask.auth.payload.response.ApiResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiError error;
        HttpStatus status;

        if (authException.getCause() instanceof CustomException) {
            CustomException ex = (CustomException) authException.getCause();
            status = ex.getHttpStatus();
            error = buildApiError(
                    ex.getErrorCode(),
                    ex.getErrorTitle(),
                    ex.getErrorDescription(),
                    ex.getErrorUri(),
                    status,
                    ex.getClass().getSimpleName(),
                    null);
        } else if (authException instanceof CustomAuthException) {
            CustomAuthException ex = (CustomAuthException) authException;
            status = ex.getHttpStatus();
            error = buildApiError(
                    ex.getErrorCode(),
                    ex.getErrorTitle(),
                    ex.getErrorDescription(),
                    ex.getErrorUri(),
                    status,
                    ex.getClass().getSimpleName(),
                    null);
        } else {
            ErrorCodes errorCode;
            if (authException.getMessage() != null && authException.getMessage().contains("expired")) {
                errorCode = ErrorCodes.EXPIRED_JWT_TOKEN;
                status = HttpStatus.UNAUTHORIZED;
            } else if (authException.getMessage() != null && authException.getMessage().contains("invalid")) {
                errorCode = ErrorCodes.INVALID_JWT_TOKEN;
                status = HttpStatus.UNAUTHORIZED;
            } else {
                errorCode = ErrorCodes.NOT_AUTHENTICATED;
                status = HttpStatus.UNAUTHORIZED;
            }

            error = buildApiError(
                    errorCode.getCode(),
                    errorCode.getTitle(),
                    errorCode.getMessage(),
                    errorCode.getUri(),
                    status,
                    authException.getClass().getSimpleName(),
                    null);
        }

        response.setStatus(status.value());

        ApiResponse<?> apiResponse = buildErrorResponse(error, status);

        String json = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(json);
        response.getWriter().flush();
        response.getWriter().close();
    }

    private ApiError buildApiError(
            String code,
            String title,
            String description,
            String uri,
            HttpStatus status,
            String type,
            Map<String, String> extraData) {
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

    private ApiResponse<?> buildErrorResponse(ApiError error, HttpStatus status) {
        return ApiResponse.<Object>builder()
                .success(false)
                .error(error)
                .timestamp(ZonedDateTime.now())
                .build();
    }
}
