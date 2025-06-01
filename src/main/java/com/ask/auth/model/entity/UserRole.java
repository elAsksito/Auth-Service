package com.ask.auth.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_roles")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRoleId.class)
public class UserRole {
	@Id
	@Column(name = "user_id", length = 36)
	private String userId;

	@Id
	@Column(name = "role_id", length = 36)
	private String roleId;

	@Id
	@Column(name = "assigned_at", nullable = false)
	private LocalDateTime assignedAt;

	@Column(name = "revoked_at")
	private LocalDateTime revokedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_roles_user"))
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_roles_role"))
	private Role role;

	@PrePersist
	public void prePersist() {
		if (assignedAt == null) {
			assignedAt = LocalDateTime.now();
		}
	}
}