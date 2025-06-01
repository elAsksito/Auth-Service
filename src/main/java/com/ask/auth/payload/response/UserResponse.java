package com.ask.auth.payload.response;


import java.time.format.DateTimeFormatter;

import com.ask.auth.model.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String email;
    private boolean isEmailVerified;
    private String status;
    private boolean twoFaEnabled;
    private String createdAt;
    private String lastLogin;
    private String updatedAt;
    private int version;
    private boolean mustChangePassword;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .isEmailVerified(user.isEmailVerified())
            .status(user.getStatus() != null ? user.getStatus().name() : null)
            .twoFaEnabled(user.isTwoFaEnabled())
            .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().format(formatter) : null)
            .lastLogin(user.getLastLogin() != null ? user.getLastLogin().format(formatter) : null)
            .updatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().format(formatter) : null)
            .version((int) user.getVersion())
            .mustChangePassword(user.isMustChangePassword())
            .build();
    }
}