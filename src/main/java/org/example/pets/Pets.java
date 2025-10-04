package org.example.pets;

import jakarta.validation.constraints.*;
import org.example.validation.ValidHappiness;
import org.example.validation.ValidHunger;
import org.example.validation.ValidName;

public record Pets (Long id,
                    @NotBlank @ValidName String name,
                    @NotBlank @ValidHunger String hungerLevel,
                    @NotBlank @ValidHappiness String happiness) {}
