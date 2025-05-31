package com.ask.auth.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ask.auth.model.entity.AuditLog;
import com.ask.auth.model.entity.User;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUser(User user);
    List<AuditLog> findByUserAndCreatedAtAfter(User user, Instant since);
    List<AuditLog> findByIpAddress(String ipAddress);
}