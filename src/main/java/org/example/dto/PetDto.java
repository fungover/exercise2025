package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (NO logic)
 * Contains data that transfers between
 * Frontend (JSON) & Backend (Java)
 * This data defines information about pets
 * Checked if valid with jakarta validation annotations
 **/

public class PetDto {
    private Long id;
    /// every pet gets a unique ID, only available in the class

    @NotBlank(message = "name is required")
    @Size(max = 50, message = "name max 50 chars")

    private String name;

    @NotBlank(message = "species is required")
    @Size(max = 30, message = "species max 30 chars")

    private String species;

    @Min(0)
    @Max(100)
    private int hungerLevel;

    @Min(0)
    @Max(100)
    private int happiness;

    /// Default constructor needed when objects are to be created through incoming JSON data
    public PetDto() {
    }

    /// Constructor that allows creation of object with all values
    public PetDto(Long id, String name, String species, int hungerLevel, int happiness) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    ///  Getters & Setters - needed to access private fields from the outside
    ///  JSON-converter uses these when transferring data between server & client

    ///  Gets pet ID
    public Long getId() {
        return id;
    }

    ///  Sets new pet ID
    public void setId(Long id) {
        this.id = id;
    }

    ///  Gets pet name
    public String getName() {
        return name;
    }

    ///  Changes pet name
    public void setName(String name) {
        this.name = name;
    }

    /// Gets pet species
    public String getSpecies() {
        return species;
    }

    /// Changes pet species
    public void setSpecies(String species) {
        this.species = species;
    }

    /// Gets pet hunger level
    public int getHungerLevel() {
        return hungerLevel;
    }

    /// Sets pet new hunger level
    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    ///  Gets pet happiness level
    public int getHappiness() {
        return happiness;
    }

    /// Sets new happiness level
    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

}
