package org.example.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserCheck(@NotBlank String password, @NotBlank String email) {
}
