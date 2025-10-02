package org.example.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PetDTO {

    private Long id; // Unique identifier for the pet, using Long here. We could have used a String (UUID) as well. But for simplicity, we use Long.


    @NotBlank(message = "Name cannot be blank") // Validation: name must not be blank
    @Size(max = 50, message = "Name cannot be longer than 50 characters") // Validation: name max length 50
    private String name;

    @NotBlank(message = "Species cannot be blank")
    @Size(max = 50, message = "Species cannot be longer than 50 characters")
    private String species;

    @Min(value = 0, message = "Hunger level must be between 0 and 100") // Validation: hunger level between 0 and 100
    @Max(value = 100, message = "Hunger level must be between 0 and 100") // Validation: hunger level between 0 and 100
    private int hungerLevel;

    @Min(value = 0, message = "Happiness must be between 0 and 100") // Validation: happiness between 0 and 100
    @Max(value = 100, message = "Happiness must be between 0 and 100") // Validation: happiness between 0 and 100
    private int happiness;

    public PetDTO() { // Default constructor needed for JSON deserialization
    }

    public PetDTO(String name, String species, int hungerLevel, int happiness) {
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
