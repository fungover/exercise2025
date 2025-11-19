package org.example.dto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        @NotEmpty(message = "Username must be inserted")
        String userName,
        @NotEmpty(message = "Password must be inserted")
        String password) {
}
