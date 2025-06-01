package com.ask.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ask.auth.model.entity.PasswordHistory;
import com.ask.auth.model.entity.User;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistory, String> {
    List<PasswordHistoryRepository> findByUserOrderByChangedAtDesc(User user);
    boolean existsByUserAndPasswordHash(User user, String passwordHash);
}