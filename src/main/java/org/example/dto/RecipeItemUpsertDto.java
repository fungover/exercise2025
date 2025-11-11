package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RecipeItemUpsertDto(
        @NotBlank(message = "Name is required")
        String name,

        @Positive(message = "Amount must be positive")
        double amount,

        @NotBlank(message = "Unit is required")
        String unit
) {
}
