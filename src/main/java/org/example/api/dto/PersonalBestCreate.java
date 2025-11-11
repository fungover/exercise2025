package org.example.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PersonalBestCreate(
        @NotNull Long exerciseId,
        @NotNull @Min(1) Integer reps,
        @NotNull @Positive BigDecimal weightKg,
        @NotNull @PastOrPresent LocalDate achievedOn
) {
}
