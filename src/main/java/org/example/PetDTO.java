package org.example;
import jakarta.validation.constraints.*;

public class PetDTO {
    @NotBlank(message = "Name can't be blank")
    private String name;

    @NotBlank(message = "Species can't be blank")
    private String species;

    @Min(value = 0, message = "Hunger can't go below 0")
    @Max(value = 100, message = "Hunger can't go above 100")
    private int hungerLevel;

    @Min(value = 0, message = "Happiness can't be below 0")
    @Max(value = 100, message = "Happiness can't go above 100")
    private int happiness;

    public PetDTO(String name, String species, int hungerLevel, int happiness) {
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }
}
