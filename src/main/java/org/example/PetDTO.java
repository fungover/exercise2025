package org.example;

public class PetDTO {
    private String name;
    private String species;
    private int hungerLevel;
    private int happiness;

    public PetDTO(String name, String species, int hungerLevel, int happiness) {
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }
}
