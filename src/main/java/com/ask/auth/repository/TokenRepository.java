package com.ask.auth.repository;

import com.ask.auth.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    List<Token> findByUserIdAndIsRevokedFalse(String userId);
    Optional<Token> findByIdAndIsRevokedFalse(String id);
}