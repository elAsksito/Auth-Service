package com.ask.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "login_events", indexes = { @Index(name = "idx_login_user_time", columnList = "user_id, timestamp") })
@Data
@Builder
public class LoginEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_login_event_user"))
	private User user;

	@Column(name = "ip_address", length = 45)
	private String ipAddress;

	@Column(name = "user_agent", length = 512)
	private String userAgent;

	@Column(name = "device_fingerprint", length = 255)
	private String deviceFingerprint;

	@Column(nullable = false)
	private Boolean success;

	@Column(name = "failure_reason", length = 255)
	private String failureReason;

	@Column(name = "geo_country", length = 100)
	private String geoCountry;

	@Column(name = "geo_city", length = 100)
	private String geoCity;

	@Column(name = "timestamp", nullable = false)
	private Instant timestamp;

	@PrePersist
	public void prePersist() {
		if (timestamp == null) {
			timestamp = Instant.now();
		}
	}
}