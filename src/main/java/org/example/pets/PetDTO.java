package org.example.pets;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PetDTO {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    private String name;

    @NotBlank(message = "Type cannot be empty")
    @Size(min = 1, max = 50, message = "Type must be between 1 and 50 characters")
    private String type;

    @Min(value = 0, message = "Hunger level must be between 0 and 10")
    @Max(value = 10, message = "Hunger level must be between 0 and 10")
    private int hungerLevel;

    @Min(value = 0, message = "Happiness level must be between 0 and 10")
    @Max(value = 10, message = "Happiness level must be between 0 and 10")
    private int happinessLevel;

    // Default constructor
    public PetDTO() {
    }

    public PetDTO(Long id, String name, String type, int hungerLevel, int happinessLevel) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hungerLevel = hungerLevel;
        this.happinessLevel = happinessLevel;
    }

    // Getters
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getHungerLevel() {
        return hungerLevel;
    }
    public int getHappinessLevel() {
        return happinessLevel;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }
    public void setHappinessLevel(int happinessLevel) {
        this.happinessLevel = happinessLevel;
    }


}
