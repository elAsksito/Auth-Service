package com.ask.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

import com.ask.auth.model.enums.Severity;

@Entity
@Table(name = "suspicious_activity", indexes = {
		@Index(name = "idx_suspicious_user_time", columnList = "user_id, timestamp") })
@Data
@Builder
public class SuspiciousActivity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_suspicious_user"))
	private User user;

	@Column(name = "ip_address", length = 45)
	private String ipAddress;

	@Column(name = "user_agent", length = 512)
	private String userAgent;

	@Column(name = "device_fingerprint", length = 255)
	private String deviceFingerprint;

	@Column(columnDefinition = "TEXT")
	private String reason;

	@Enumerated(EnumType.STRING)
	@Column(length = 10, nullable = false)
	private Severity severity = Severity.MEDIUM;

	@Column(columnDefinition = "json")
	private String details;

	@Column(nullable = false)
	private Instant timestamp;

	@PrePersist
	public void prePersist() {
		if (timestamp == null)
			timestamp = Instant.now();
	}
}