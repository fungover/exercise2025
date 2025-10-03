package org.example;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PetDTO {
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Species is mandatory")
    private String species;
    @NotNull(message = "Hunger level is required")
    @Min(value = 0, message = "Hunger level must be at least 0")
    @Max(value = 100, message = "Hunger level must be at most 100")
    private Integer hungerLevel;
    @NotNull(message = "Happiness is required")
    @Min(value = 0, message = "Happiness must be at least 0")
    @Max(value = 100, message = "Happiness must be at most 100")
    private Integer happiness;

    public PetDTO() {
    }

    public PetDTO(Long id, String name, String species, Integer hungerLevel, Integer happiness) {
        this.id = id;
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
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getSpecies() {
        return species;
    }
    public void setSpecies(String species) {
        this.species = species;
    }

    public Integer getHungerLevel() {
        return hungerLevel;
    }
    public void setHungerLevel(Integer hungerLevel) {
        this.hungerLevel = hungerLevel;
    }
    public Integer getHappiness() {
        return happiness;
    }
    public void setHappiness(Integer happiness) {
        this.happiness = happiness;
    }
}
