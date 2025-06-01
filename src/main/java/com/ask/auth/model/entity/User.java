package com.ask.auth.model.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.UuidGenerator;

import com.ask.auth.model.enums.Status;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@Column(length = 36)
	@GeneratedValue(generator = "UUID")
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
	private String id;

	@Column(length = 150, nullable = false, unique = true)
	private String email;

	@Column(length = 255, nullable = false)
	private String password;

	@Builder.Default
	@Column(name = "is_email_verified", nullable = false)
	private boolean isEmailVerified = false;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(length = 8, nullable = false, columnDefinition = "ENUM('ACTIVE', 'BANNED', 'INACTIVE') DEFAULT 'ACTIVE'")
	private Status status = Status.ACTIVE;

	@Builder.Default
	@Column(name = "failed_login_attempts", nullable = false)
	private int failedLoginAttempts = 0;

	@Column(name = "lock_until")
	private LocalDateTime lockUntil;

	@Builder.Default
	@Column(name = "two_fa_enabled", nullable = false)
	private boolean twoFaEnabled = false;

	@Column(name = "two_fa_secret", length = 512)
	private byte[] twoFaSecret;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Version
	@Column(nullable = false)
	private long version;

	@Column(name = "password_last_changed")
	private LocalDateTime passwordLastChanged;

	@Builder.Default
	@Column(name = "must_change_password", nullable = false)
	private boolean mustChangePassword = false;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserRole> userRoles;

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
		updatedAt = createdAt;
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = LocalDateTime.now();
	}
}