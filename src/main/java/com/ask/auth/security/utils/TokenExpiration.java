package com.ask.auth.security.utils;

import lombok.Getter;

@Getter
public enum TokenExpiration {

    ACCESS(1),
    REFRESH(1),
    SESSION(1);

    private final long seconds;

    TokenExpiration(long seconds) {
        this.seconds = seconds;
    }

    public long getSeconds() {
        return seconds;
    }
}