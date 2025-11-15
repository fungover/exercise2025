package org.example.dto.user;

import jakarta.validation.constraints.NotBlank;

public record User(@NotBlank String name,
                   @NotBlank String password,
                   @NotBlank String email) {
}
