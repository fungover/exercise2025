package org.example.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCheck(@NotBlank String password, @NotBlank String email) {
}
