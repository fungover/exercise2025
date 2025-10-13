package org.example.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PetDTO {
    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    @NotBlank(message = "speciest cannot be blank")
    private String species;

    @Min(value = 0, message = "Minimum value for hungerLevel is 0")
    @Max(value = 100, message = "Max value for hunger level is 100")
    private int hungerLevel;

    @Min(value = 0, message = "Minimum value for happiness is 0")
    @Max(value = 100, message = "Max value for happiness is 100")
    private int happiness;

    public PetDTO() {}

    //constructor
    public PetDTO(Long id, String name, String species, int hungerLevel, int happiness) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    //getters and setters
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
