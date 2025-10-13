package org.fungover.dto;

import jakarta.validation.constraints.*;

public class PetDTO {
    private long id;

    @Min(value = 0)
    @Max(value = 10)
    private int happiness;

    @NotBlank(message = "name is required")
    @Size(min = 2, max = 50, message = "name must be longer than 2 chars and less than 50")
    private String name;

    @NotBlank(message = "species is required")
    private String species;

    @Min(value = 0)
    @Max (value = 10)
    private int hungerLevel;

    public long getId() {
        return id;
    }
    public int getHappiness() {
        return happiness;
    }
    public String getName() {
        return name;
    }
    public String getSpecies() {
        return species;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setHappiness(int happiness) {
        this.happiness = happiness;
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
    public int getHungerLevel() {
        return hungerLevel;
    }
}
