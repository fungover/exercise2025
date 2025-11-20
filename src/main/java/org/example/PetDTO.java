package org.example;

public record PetDTO(String name, String species, int hunger, int happiness, Integer id) {
    public int getHunger() {
        return hunger;
    }
    public int getHappiness() {
        return happiness;
    }
    public String getName() {
        return name;
    }
    public String getSpecies() {
        return species;
    }
    public Integer getId() {
        return id;
    }
}
