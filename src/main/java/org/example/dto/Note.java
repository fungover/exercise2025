package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record Note(Long id,
                   @NotNull String value,
                   Long userId,
                   LocalDateTime createdAt) {
}
