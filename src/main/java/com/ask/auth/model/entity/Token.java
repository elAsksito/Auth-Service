package com.ask.auth.model.entity;

import java.time.Instant;

import org.hibernate.annotations.UuidGenerator;

import com.ask.auth.model.enums.TokenType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens", indexes = { @Index(name = "idx_user_expiration", columnList = "user_id, expires_at"),
		@Index(name = "idx_token_revoked", columnList = "is_revoked") })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
	@Id
	@Column(length = 36)
	@GeneratedValue(generator = "UUID")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_token_user"))
	private User user;

	@Column(name = "token_hash", nullable = false, length = 512)
	private byte[] tokenHash;

	@Enumerated(EnumType.STRING)
	@Column(name = "token_type", nullable = false, length = 10)
	private TokenType tokenType;

	@Column(name = "user_agent", length = 512)
	private String userAgent;

	@Column(name = "ip_address", length = 45)
	private String ipAddress;

	@Builder.Default
	@Column(name = "is_revoked", nullable = false)
	private boolean isRevoked = false;

	@Column(name = "revoked_at")
	private Instant revokedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_token_id", foreignKey = @ForeignKey(name = "fk_token_parent"))
	private Token parentToken;

	@Column(name = "expires_at", nullable = false)
	private Instant expiresAt;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();
	}
}