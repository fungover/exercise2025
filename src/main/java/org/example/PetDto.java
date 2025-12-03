package org.example;

import java.time.LocalDateTime;

public record PetDto(Integer id,String species, String name, int age, String birthDate, LocalDateTime createdAt) {
}
