package com.ask.auth.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ask.auth.model.entity.Role;
import com.ask.auth.model.entity.UserRole;
import com.ask.auth.repository.RoleRepository;
import com.ask.auth.repository.UserRoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public CompletableFuture<Optional<Role>> findByName(String name) {
        return CompletableFuture.completedFuture(roleRepository.findByName(name));
    }

    @Transactional
    public CompletableFuture<UserRole> assignRoleToUser(String userId, String roleId) {

        List<UserRole> existingRoles = userRoleRepository.findByUserId(userId);
        boolean alreadyAssigned = existingRoles.stream()
                .anyMatch(ur -> ur.getRoleId().equals(roleId) && ur.getRevokedAt() == null);

        if(alreadyAssigned) {
            throw new IllegalStateException("Role already assigned to user");
        }

        UserRole userRole = UserRole.builder()
                .userId(userId)
                .roleId(roleId)
                .assignedAt(LocalDateTime.now())
                .revokedAt(null)
                .build();

        userRoleRepository.save(userRole);
        return CompletableFuture.completedFuture(userRole);
    }

    public List<UserRole> getUserRoles(String userId) {
        List<UserRole> roles = userRoleRepository.findActiveUserRolesWithRole(userId);

        roles.forEach(ur -> {
            if (ur.getRole() != null) {
                ur.getRole().getName();
            }
        });

        return List.copyOf(roles);

    }

    @Transactional
    public UserRole revokeUserRole(String userId, String roleId) {
        UserRole userRole = userRoleRepository.findByUserIdAndRoleIdAndRevokedAtIsNull(userId, roleId)
                .orElseThrow(() -> new IllegalStateException("Role not assigned or already revoked for user"));

        userRole.setRevokedAt(LocalDateTime.now());
        return userRoleRepository.save(userRole);
}

}