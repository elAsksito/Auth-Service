package com.ask.auth.service;

import org.springframework.stereotype.Service;

import com.ask.auth.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    
    
}