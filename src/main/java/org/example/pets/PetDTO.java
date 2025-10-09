package org.example.pets;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;



/*
Define a Pet DTO class with Bean Validation for the fields with
name, species, hungerLevel and happiness.
*/
public class PetDTO {
    @NotBlank (message = "Name cannot be empty")
    private String name;

    @NotBlank (message = "Species cannot be empty")
    private String species;

    @Min(value = 0, message = "Hunger level cannot be below 0")
    @Max(value = 100, message = "Hunger level cannot be above 100")
    private int hungerLevel = 50;

    @Min(value = 0, message = "Happiness cannot be below 0")
    @Max(value = 100, message = "Happiness cannot be above 100")
    private int happiness = 50;

    public PetDTO() {}

    public PetDTO(String name, String species) {
        this.name = name;
        this.species = species;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public int getHungerLevel() { return hungerLevel; }
    public void setHungerLevel(int hungerLevel) { this.hungerLevel = hungerLevel; }

    public int getHappiness() { return happiness; }
    public void setHappiness(int happiness) { this.happiness = happiness; }
}
