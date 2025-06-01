package com.ask.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ask.auth.model.entity.OneTimeToken;
import com.ask.auth.model.entity.User;
import com.ask.auth.model.enums.OneTokenType;

@Repository
public interface OneTimeTokenRepository extends JpaRepository<OneTimeToken, String> {
    Optional<OneTimeToken> findByIdAndIsUsedFalse(String id);
    List<OneTimeToken> findByUserAndTokenTypeAndIsUsedFalse(User user, OneTokenType tokenType);
    boolean existsByUserAndTokenTypeAndIsUsedFalse(User user, OneTokenType tokenType);
}