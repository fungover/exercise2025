package org.example.dto;

public class PetDTO {
    private Long id;
    private String name;
    private String species;
    private int hungerLevel;
    private int happiness;

    public PetDTO() {}

    public PetDTO(Long id, String name, String species, int hungerLevel, int happiness) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    // getters/setters
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

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }
}
