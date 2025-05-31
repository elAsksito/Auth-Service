package com.ask.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "failed_login_attempts_ip")
@Data
@Builder
public class FailedLoginAttemptsIp {
	@Id
	@Column(name = "ip_address", length = 45)
	private String ipAddress;

	@Builder.Default
	@Column(nullable = false)
	private int attempts = 0;

	@Column(name = "last_attempt")
	private Instant lastAttempt;

	@Column(name = "blocked_until")
	private Instant blockedUntil;
}