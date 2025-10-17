package org.example.animal;

public class Animal {
    private final String name;
    private final String species;

    public Animal(String name, String species) {
        this.name = name;
        this.species = species;
    }

    public String name() {
        return name;
    }

    public String species() {
        return species;
    }
}
