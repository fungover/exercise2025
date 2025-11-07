package org.example.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record User(@NotNull @NotBlank String name,
                   @NotNull @NotBlank String password,
                   @NotNull @NotBlank String email) {
}
