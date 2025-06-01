package com.ask.auth.repository;

import com.ask.auth.model.entity.Session;
import com.ask.auth.model.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, String> {
    List<Session> findByUserAndIsRevokedFalse(User user);
    List<Session> findByUserIdAndIsRevokedFalse(String userId);
    Optional<Session> findByIdAndIsRevokedFalse(String id);
    Optional<Session> findByUserId(String userId);
}