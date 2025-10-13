package org.example.pets;

public record PetDTO(String name, String species,
                     int hungerLevel, int happiness) {

    public PetDTO feed() {
        return new PetDTO(name, species, hungerLevel - 1, happiness);
    }

    public PetDTO play() {
        return new PetDTO(name, species, hungerLevel, happiness + 1);
    }
}
