package org.example.pets;


/*
Define a Pet DTO class with Bean Validation for the fields with
name, species, hungerLevel and happiness.
*/
public class PetDTO {
    private String name;
    private String species;


    public PetDTO() {}

    public PetDTO(String name, String species) {
        this.name = name;
        this.species = species;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }
}
