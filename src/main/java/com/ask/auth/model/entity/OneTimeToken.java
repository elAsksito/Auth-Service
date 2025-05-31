package com.ask.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

import com.ask.auth.model.enums.OneTokenType;

@Entity
@Table(name = "one_time_tokens", indexes = {
		@Index(name = "idx_ott_user_type_used", columnList = "user_id, token_type, is_used") })
@Data
@Builder
public class OneTimeToken {

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_ott_user"))
	private User user;

	@Column(name = "token_hash", nullable = false, length = 512)
	private byte[] tokenHash;

	@Enumerated(EnumType.STRING)
	@Column(name = "token_type", nullable = false, length = 30)
	private OneTokenType tokenType;

	@Column(name = "is_used", nullable = false)
	private Boolean isUsed = false;

	@Column(name = "used_at")
	private Instant usedAt;

	@Column(name = "expires_at", nullable = false)
	private Instant expiresAt;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@PrePersist
	public void prePersist() {
		if (createdAt == null)
			createdAt = Instant.now();
	}
}