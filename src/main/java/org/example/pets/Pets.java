package org.example.pets;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.validation.ValidHappiness;
import org.example.validation.ValidHunger;
import org.example.validation.ValidName;

public record Pets (Long id,
                    @ValidName String name,
                    @ValidHunger String hungerLevel,
                    @ValidHappiness String happiness) {}
