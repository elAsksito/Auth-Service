package com.ask.auth.repository;

import com.ask.auth.model.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, String> {
    List<Session> findByUserIdAndIsRevokedFalse(String userId);
    Optional<Session> findByIdAndIsRevokedFalse(String id);
}