package com.ask.auth.payload.response;

import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ApiError error;
    private ZonedDateTime timestamp;
}