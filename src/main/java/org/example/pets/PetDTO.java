package org.example.pets;

import jakarta.validation.constraints.*;
import jakarta.ws.rs.ApplicationPath;


public class PetDTO {
    @NotBlank(message = "Name can't be blank.")
    String name;
    @NotBlank(message = "Species can't be blank.")
    String species;
    @Max(value = 100, message = "Hunger level can't be higher than 100.")
    @Min(value = 0, message = "Hunger level can't be lower than 0.")
    int hungerLevel;
    @Max(value = 100, message = "Happiness can't be higher than 100.")
    @Min(value = 0, message = "Happiness can't be lower than 0.")
    int happiness;
    long id;


    public PetDTO(String name, String species, int hungerLevel, int happiness) {
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }
    public PetDTO() {}
    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }
}
