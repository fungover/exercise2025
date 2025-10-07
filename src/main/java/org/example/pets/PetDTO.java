package org.example.pets;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.validation.ValidHappinessLevel;
import org.example.validation.ValidHungerLevel;
import org.example.validation.ValidName;
import org.example.validation.ValidType;

public class PetDTO {
    private Long id;

    @ValidName
    private String name;

    @ValidType
    private String type;

    @ValidHungerLevel
    private int hungerLevel;

    @ValidHappinessLevel
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
