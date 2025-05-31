package com.ask.auth.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ask.auth.model.entity.LoginEvent;
import com.ask.auth.model.entity.User;

@Repository
public interface LoginEventRepository extends JpaRepository<LoginEvent, Long> {
    List<LoginEvent> findByUser(User user);
    List<LoginEvent> findByUserAndTimestampAfter(User user, Instant after);
    List<LoginEvent> findByIpAddress(String ipAddress);
    List<LoginEvent> findByDeviceFingerprint(String deviceFingerprint);
}