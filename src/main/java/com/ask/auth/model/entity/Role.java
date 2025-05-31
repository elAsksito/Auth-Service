package com.ask.auth.model.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
@Builder
public class Role {
	@Id
	@Column(length = 36)
	private String id;

	@Column(length = 50, nullable = false, unique = true)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserRole> userRoles;
}