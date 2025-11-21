package org.example;

import java.time.LocalDateTime;

public record PetDto(Integer id, String name, int age, String species, int hungerLevel, int happiness, LocalDateTime createdAt) {}
