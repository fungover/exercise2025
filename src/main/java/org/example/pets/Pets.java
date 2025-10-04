package org.example.pets;

import org.example.validation.ValidHappiness;
import org.example.validation.ValidHunger;
import org.example.validation.ValidName;

public record Pets (Long id,
                    @ValidName String name,
                    @ValidHunger String hungerLevel,
                    @ValidHappiness String happiness) {}
