package org.example.pets;

import org.example.validation.ValidLevel;
import org.example.validation.ValidName;
import org.example.validation.ValidSpecies;

public record PetDTO(@ValidName String name, @ValidSpecies String species,
                     @ValidLevel int hungerLevel, @ValidLevel int happiness) {

    public PetDTO feed() {
        return new PetDTO(name, species, hungerLevel - 1, happiness);
    }

    public PetDTO play() {
        return new PetDTO(name, species, hungerLevel, happiness + 1);
    }
}
