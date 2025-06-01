package com.ask.auth.repository;

import com.ask.auth.model.entity.UserRole;
import com.ask.auth.model.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUserId(String userId);
    List<UserRole> findByRoleId(String roleId);
    Optional<UserRole> findByUserIdAndRoleIdAndRevokedAtIsNull(String userId, String roleId);

    @Query("SELECT ur FROM UserRole ur JOIN FETCH ur.role WHERE ur.userId = :userId AND ur.revokedAt IS NULL")
    List<UserRole> findActiveUserRolesWithRole(String userId);
}