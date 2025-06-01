package com.ask.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "sessions", indexes = { @Index(name = "idx_session_user_expires", columnList = "user_id, expires_at"),
		@Index(name = "idx_session_revoked", columnList = "is_revoked") })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_session_user"))
	private User user;

	@Column(name = "session_token_hash", nullable = false, length = 512)
	private byte[] sessionTokenHash;

	@Column(name = "user_agent", length = 512)
	private String userAgent;

	@Column(name = "ip_address", length = 45)
	private String ipAddress;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@Column(name = "expires_at", nullable = false)
	private Instant expiresAt;

	@Builder.Default
	@Column(name = "is_revoked", nullable = false)
	private boolean isRevoked = false;

	@Column(name = "revoked_at")
	private Instant revokedAt;

	@Column(name = "last_activity")
	private Instant lastActivity;

	@PrePersist
	public void prePersist() {
		if (createdAt == null)
			createdAt = Instant.now();
	}
}