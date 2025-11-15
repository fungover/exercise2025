package org.example.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record User(@NotBlank String name,
                   @NotBlank String password,
                   @NotBlank String email) {
}
