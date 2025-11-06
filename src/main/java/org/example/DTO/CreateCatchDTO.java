package org.example.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.OffsetDateTime;

public record CreateCatchDTO(
        @NotBlank(message = "Species cannot be blank")
        String species,

        @NotNull(message = "Weight is required")
        @Positive(message = "Weight must be positive")
        Double weight,

        @NotNull(message = "Length is required")
        @Positive(message = "Length must be positive")
        Double length
) {}