package org.example.DTO;

import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;


public record CatchDto(
        @NotBlank Long id,
        @NotBlank String species,
        @Positive Double weight,
        @Positive Double length,
        @NotBlank OffsetDateTime caughtAt
) {}