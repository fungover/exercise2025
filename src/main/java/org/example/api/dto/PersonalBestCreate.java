package org.example.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PersonalBestCreate(
        @NotNull Long exerciseId,
        @NotNull @Min(1) Integer reps,
        @NotNull BigDecimal weightKg,
        @NotNull LocalDate achievedOn
) {
}
