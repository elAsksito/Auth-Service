package com.ask.auth.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final String errorCode;
    private final String errorTitle;
    private final String errorDescription;
    private final String errorUri;
    private final HttpStatus httpStatus;

    public CustomException(String errorCode, String errorTitle, String errorDescription, String errorUri, HttpStatus httpStatus) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
        this.errorDescription = errorDescription;
        this.errorUri = errorUri;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public String getErrorUri() {
        return errorUri;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}