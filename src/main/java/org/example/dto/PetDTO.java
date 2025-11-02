package org.example.dto;

import java.time.LocalDateTime;

public record PetDTO(Long id, String name, String species, int hungerLevel, int happiness, LocalDateTime createdAt, LocalDateTime updatedAt) {

}
