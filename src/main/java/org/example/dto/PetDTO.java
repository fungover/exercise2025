package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * Data Transfer Object representing a Pet.
 */
public class PetDTO implements Serializable {

    private Long id;

    @NotBlank(message = "Pet name is required and cannot be blank")
    @Size(min = 2, max = 50, message = "Pet name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Species is required and cannot be blank")
    @Size(min = 2, max = 30, message = "Species must be between 2 and 30 characters")
    private String species;

    @NotNull(message = "Hunger level is required")
    @Min(value = 0, message = "Hunger level must be at least 0")
    @Max(value = 100, message = "Hunger level cannot exceed 100")
    private Integer hungerLevel;

    @NotNull(message = "Happiness is required")
    @Min(value = 0, message = "Happiness must be at least 0")
    @Max(value = 100, message = "Happiness cannot exceed 100")
    private Integer happiness;

    //-- Default constructor --
    public PetDTO() {
    }

    //-- Constructor with all fields except id--
    public PetDTO(String name, String species, Integer hungerLevel, Integer happiness) {
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    // --Constructor with all fields--
    public PetDTO(Long id, String name, String species, Integer hungerLevel, Integer happiness) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    // --Copy constructor--
    public PetDTO(PetDTO other) {
        this.id = other.id;
        this.name = other.name;
        this.species = other.species;
        this.hungerLevel = other.hungerLevel;
        this.happiness = other.happiness;
    }

    // --Getters and Setters--
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PetDTO other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PetDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", hungerLevel=" + hungerLevel +
                ", happiness=" + happiness +
                '}';
    }
}
