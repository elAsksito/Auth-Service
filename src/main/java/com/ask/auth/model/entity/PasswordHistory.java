package com.ask.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "password_history", indexes = {
		@Index(name = "idx_password_history_user", columnList = "user_id, changed_at") })
@Data
@Builder
public class PasswordHistory {

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_password_history_user"))
	private User user;

	@Column(name = "password_hash", nullable = false, length = 255)
	private String passwordHash;

	@Column(name = "changed_at", nullable = false)
	private Instant changedAt;

	@PrePersist
	public void prePersist() {
		if (changedAt == null) {
			changedAt = Instant.now();
		}
	}
}