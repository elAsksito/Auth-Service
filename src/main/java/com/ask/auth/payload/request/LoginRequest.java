package com.ask.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;
}