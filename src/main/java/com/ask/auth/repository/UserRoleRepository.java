package com.ask.auth.repository;

import com.ask.auth.model.entity.UserRole;
import com.ask.auth.model.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUserId(String userId);
    List<UserRole> findByRoleId(String roleId);
}