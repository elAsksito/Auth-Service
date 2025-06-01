package com.ask.auth.payload.response;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditLogResponse {
    private Long id;
    private Long userId;
    private String action;
    private String metadata;
    private String ipAddress;
    private String userAgent;
    private Instant createdAt;
}