package org.example.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PersonalBestView(
        Long id,
        Long exerciseId,
        String exerciseName,
        Integer reps,
        BigDecimal weightKg,
        LocalDate achievedOn
) {
}
