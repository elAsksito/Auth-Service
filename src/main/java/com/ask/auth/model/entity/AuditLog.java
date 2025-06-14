package com.ask.auth.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

import com.ask.auth.model.enums.Action;

@Entity
@Table(name = "audit_logs", indexes = { @Index(name = "idx_audit_user_time", columnList = "user_id, created_at") })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_audit_user"), nullable = true)
	private User user;

	@Enumerated(EnumType.STRING)
	private Action action;

	@Column(columnDefinition = "json")
	private String metadata;

	@Column(name = "ip_address", length = 45)
	private String ipAddress;

	@Column(name = "user_agent", length = 512)
	private String userAgent;

	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;

	@PrePersist
	public void prePersist() {
		if (createdAt == null)
			createdAt = Instant.now();
	}
}