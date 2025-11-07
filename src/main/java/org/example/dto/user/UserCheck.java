package org.example.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCheck(@NotNull @NotBlank String password, @NotNull @NotBlank String email) {
}
