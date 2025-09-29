package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PetDTO {
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String species;

    @Min(0)
    @Max(100)
    private int hungerLevel;

    @Min(0)
    @Max(100)
    private int happiness;

    // No-args constructor
    public PetDTO() {}

    // All-args constructor
    public PetDTO(String name, String species, int hungerLevel, int happiness) {
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    // Getters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public int getHappiness() {
        return happiness;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }
}
