package org.example.dto.token;

import jakarta.validation.constraints.NotBlank;

public record TokenRequest(@NotBlank String token, @NotBlank String refreshToken) {
}
