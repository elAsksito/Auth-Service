package com.ask.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ask.auth.model.entity.FailedLoginAttemptsIp;

@Repository
public interface FailedLoginAttemptsIpRepository extends JpaRepository<FailedLoginAttemptsIp, String> {
    boolean existsByIpAddress(String ipAddress);
}