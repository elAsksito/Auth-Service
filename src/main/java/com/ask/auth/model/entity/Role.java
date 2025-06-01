package com.ask.auth.model.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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