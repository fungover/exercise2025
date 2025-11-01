package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record Note(Long id,
                   @NotBlank @NotNull String value,
                   @NotBlank @NotNull Long userId,
                   LocalDateTime createdAt) {
}
