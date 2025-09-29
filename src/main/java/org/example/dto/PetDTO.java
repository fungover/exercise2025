package org.example.dto;

//imported from hibernate-validator

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class PetDTO {
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "species must not be blank")
    private String species;

    @Min(value = 0, message = "hungerLevel must be >= 0")
    @Max(value = 100, message = "hungerLevel must be <= 100")
    private int hungerLevel;

    @Min(value = 0, message = "happiness must be >= 0")
    @Max(value = 100, message = "happiness must be <= 100")
    private int happiness;

    public PetDTO() {
        /*
        required for JSON-B / deserialization
        cuz JSON-B needs an non-argument constructor to
        create an empty instance of the class and populate its fields
        via reflection or setters
         */
    }

    public PetDTO(Long id, String name, String species, int hungerLevel,
                  int happiness) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happiness = happiness;
    }

    //getters and setters
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getSpecies() {return species;}

    public void setSpecies(String species) {this.species = species;}

    public int getHungerLevel() {return hungerLevel;}

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public int getHappiness() {return happiness;}

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    //hashcode and equals


    @Override public boolean equals(Object o) {
        // same reference
        if (this == o) return true;
        //checks null or wrong type
        if (!(o instanceof PetDTO petDTO)) return false;
        //compare by id
        return Objects.equals(id, petDTO.id);
    }

    @Override public int hashCode() {return Objects.hashCode(id);}
}
