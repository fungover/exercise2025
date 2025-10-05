package org.example.petadoptionservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PetDTO(
        Long id,
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "Species is required") String species,
        @Min(0) @Max(100) int hungerLevel,
        @Min(0) @Max(100) int happiness
){}
