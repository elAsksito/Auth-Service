package com.ask.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "ip_blocks")
@Data
@Builder
public class IpBlock {

	@Id
	@Column(name = "ip_address", length = 45)
	private String ipAddress;

	@Column(name = "blocked_until", nullable = false)
	private Instant blockedUntil;

	@Column(columnDefinition = "TEXT")
	private String reason;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@PrePersist
	public void prePersist() {
		if (createdAt == null)
			createdAt = Instant.now();
	}
}