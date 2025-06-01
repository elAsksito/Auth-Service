package com.ask.auth.repository;

import com.ask.auth.model.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph(attributePaths = "userRoles")
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}