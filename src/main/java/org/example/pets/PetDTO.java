package org.example.pets;

import org.example.validation.ValidHappinessLevel;
import org.example.validation.ValidHungerLevel;
import org.example.validation.ValidName;
import org.example.validation.ValidSpecies;

public class PetDTO {
    private Long id;

    @ValidName
    private String name;

    @ValidSpecies
    private String species;

    @ValidHungerLevel
    private int hungerLevel;

    @ValidHappinessLevel
    private int happinessLevel;

    // Default constructor
    public PetDTO() {
    }

    public PetDTO(Long id, String name, String species, int hungerLevel, int happinessLevel) {
        this.id = id;
        this.name = name;
        this.species = species;
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
    public String getSpecies() {
        return species;
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
    public void setSpecies(String species) {
        this.species = species;
    }
    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }
    public void setHappinessLevel(int happinessLevel) {
        this.happinessLevel = happinessLevel;
    }


}
