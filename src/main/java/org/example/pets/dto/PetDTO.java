package org.example.pets.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PetDTO {

    private Long id;

    @NotBlank( message = "Name is required")
    private String name;

    @NotBlank( message = "Species is required")
    private String species;

    @NotNull( message = "Hunger level is required")
    @Min(value = 0, message = "Hunger level must be between 0 and 100")
    @Max(value = 100, message = "Hunger level must be between 0 and 100")
    private Integer hungerLevel;

    @NotNull( message = "Happiness level is required")
    @Min(value = 0, message = "Happiness level must be between 0 and 100")
    @Max(value = 100, message = "Happiness level must be between 0 and 100")
    private Integer happinessLevel;

    public PetDTO() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getSpecies() {return species;}
    public void setSpecies(String species) {this.species = species;}

    public Integer getHungerLevel() {return hungerLevel;}
    public void setHungerLevel(Integer hungerLevel) {this.hungerLevel = hungerLevel;}

    public Integer getHappinessLevel() {return happinessLevel;}
    public void setHappinessLevel(Integer happinessLevel) {this.happinessLevel = happinessLevel;}
}
