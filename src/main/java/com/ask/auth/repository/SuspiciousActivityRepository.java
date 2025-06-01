package com.ask.auth.repository;

import com.ask.auth.model.entity.SuspiciousActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuspiciousActivityRepository extends JpaRepository<SuspiciousActivity, Long> {
    List<SuspiciousActivity> findByUserIdOrderByTimestampDesc(String userId);
}