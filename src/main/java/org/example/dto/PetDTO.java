package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**

 */
public class PetDTO {

    private Long id;

    @NotBlank(message = "name must not be blank")
    @Size(max = 50, message = "name must be at most 50 characters")
    private String name;

    @NotBlank(message = "species must not be blank")
    @Size(max = 30, message = "species must be at most 30 characters")
    private String species;

    @Min(value = 0, message = "hungerLevel must be between 0 and 100")
    @Max(value = 100, message = "hungerLevel must be between 0 and 100")
    private int hungerLevel;

    @Min(value = 0, message = "happiness must be between 0 and 100")
    @Max(value = 100, message = "happiness must be between 0 and 100")
    private int happiness;


    public PetDTO() {}

    public PetDTO(Long id, String name, String species, int hungerLevel, int happiness) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public int getHungerLevel() { return hungerLevel; }
    public void setHungerLevel(int hungerLevel) { this.hungerLevel = hungerLevel; }

    public int getHappiness() { return happiness; }
    public void setHappiness(int happiness) { this.happiness = happiness; }

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
