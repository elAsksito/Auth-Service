package com.ask.auth.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRoleId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private String roleId;

	private LocalDateTime assignedAt;
}