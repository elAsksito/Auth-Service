package com.ask.auth.repository;

import com.ask.auth.model.entity.Token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByTokenHash(byte[] tokenHash);
}