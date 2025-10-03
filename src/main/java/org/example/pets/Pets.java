package org.example.pets;

import org.example.validation.ValidName;

public record Pets (String id,
                    @ValidName String name,
                    String hungerLevel,
                    String happiness) {}
