package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * PetDTO is our Data Transfer Object (DTO) to represent a Pet
 * It will be used to recieve and send JSON data in our REST API.
 * We need this because:
 * - It defines what information a Pet must have
 * - It ensures that the data is valid (using Bean Validation)
 * - It allows automatic conversion between JSON <-> Java object
 */
public class PetDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Species cannot be empty")
    private String species;

    @Min(value = 0, message = "Hunger level must be at least 0")
    @Max(value = 100, message = "Hunger level must be at most 100")
    private int hungerLevel;

    @Min(value = 0, message = "Happiness level must be at least 0")
    @Max(value = 100, message = "Happiness level must be at most 100")
    private int happiness;

    // Empty constructor needed by Jakarta Bean Validation
    public PetDTO() {}

    // Full constructor. Useful when we want to create a PetDTO in code
    public PetDTO(String name, String species, int hungerLevel, int happiness) {
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    // Getters and Setters
    // Allow us to read and update private fields
    public String getName() {
        return name;
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
