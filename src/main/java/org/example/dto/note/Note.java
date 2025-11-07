package org.example.dto.note;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record Note(Long id,
                   @NotNull String value,
                   Long userId,
                   LocalDateTime createdAt,
                   LocalDateTime deletedAt) {
}
