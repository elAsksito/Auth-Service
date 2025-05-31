package com.ask.auth.payload;

import lombok.Getter;

@Getter
public enum ErrorCodes {
    USER_NOT_FOUND(
        "AUTH-001",
        "https://docs.ask.com/errors/USR-001",
        "error.user.not_found.title",
        "error.user.not_found.description"
    ),
    INVALID_PASSWORD(
        "AUTH-002",
        "https://docs.ask.com/errors/AUTH-002",
        "error.auth.invalid_password.title",
        "error.auth.invalid_password.description"
    ),
    ACCOUNT_LOCKED(
        "AUTH-003",
        "https://docs.ask.com/errors/AUTH-003",
        "error.auth.account_locked.title",
        "error.auth.account_locked.description"
    ),
    IP_BLOCKED(
        "AUTH-004",
        "https://docs.ask.com/errors/AUTH-004",
        "error.auth.ip_blocked.title",
        "error.auth.ip_blocked.description"
    ),
    INVALID_JWT_TOKEN(
        "AUTH-005",
        "https://docs.ask.com/errors/AUTH-005",
        "error.auth.invalid_jwt_token.title",
        "error.auth.invalid_jwt_token.description"
    ),
    UNAUTHORIZED_ACCESS(
        "AUTH-006",
        "https://docs.ask.com/errors/AUTH-006",
        "error.auth.unauthorized_access.title",
        "error.auth.unauthorized_access.description"
    ),
    TOO_MANY_LOGIN_ATTEMPTS(
        "AUTH-007",
        "https://docs.ask.com/errors/AUTH-007",
        "error.auth.too_many_login_attempts.title",
        "error.auth.too_many_login_attempts.description"
    ),
    USER_ALREADY_EXISTS(
        "AUTH-008",
        "https://docs.ask.com/errors/AUTH-008",
        "error.auth.user_already_exists.title",
        "error.auth.user_already_exists.description"
    ),
    EMAIL_NOT_VERIFIED(
        "AUTH-009",
        "https://docs.ask.com/errors/AUTH-009",
        "error.auth.email_not_verified.title",
        "error.auth.email_not_verified.description"
    ),
    INVALID_PASSWORD_RESET_TOKEN(
        "AUTH-010",
        "https://docs.ask.com/errors/AUTH-010",
        "error.auth.invalid_password_reset_token.title",
        "error.auth.invalid_password_reset_token.description"
    ),
    SESSION_EXPIRED(
        "AUTH-011",
        "https://docs.ask.com/errors/AUTH-011",
        "error.auth.session_expired.title",
        "error.auth.session_expired.description"
    ),
    USER_INACTIVE(
        "AUTH-012",
        "https://docs.ask.com/errors/AUTH-012",
        "error.auth.user_inactive.title",
        "error.auth.user_inactive.description"
    ),
    RESOURCE_ACCESS_DENIED(
        "AUTH-013",
        "https://docs.ask.com/errors/AUTH-013",
        "error.auth.resource_access_denied.title",
        "error.auth.resource_access_denied.description"
    ),
    ACCOUNT_EXPIRED(
        "AUTH-014",
        "https://docs.ask.com/errors/AUTH-014",
        "error.auth.account_expired.title",
        "error.auth.account_expired.description"
    ),
    USER_CREATION_FAILED(
        "AUTH-015",
        "https://docs.ask.com/errors/AUTH-015",
        "error.auth.user_creation_failed.title",
        "error.auth.user_creation_failed.description"
    ),
    WEAK_PASSWORD(
        "AUTH-016",
        "https://docs.ask.com/errors/AUTH-016",
        "error.auth.weak_password.title",
        "error.auth.weak_password.description"
    );

    private final String code;
    private final String uri;
    private final String titleKey;
    private final String descriptionKey;

    ErrorCodes(String code, String uri, String titleKey, String descriptionKey) {
        this.code = code;
        this.uri = uri;
        this.titleKey = titleKey;
        this.descriptionKey = descriptionKey;
    }
}