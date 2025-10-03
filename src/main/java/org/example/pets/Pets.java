package org.example.pets;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.validation.ValidName;

public record Pets (Long id,
                    @ValidName String name,
                    @NotNull @Size(min=1, max=10)String hungerLevel,
                    @NotNull @Size(max=5)String happiness) {}
