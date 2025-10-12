package org.example.pets;

public record PetDTO(String name, String species,
                     int hungerLevel, int happiness) {
    public PetDTO {
        hungerLevel = 5;
        happiness = 5;
    }
}
