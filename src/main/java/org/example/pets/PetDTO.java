package org.example.pets;

import org.example.validation.ValidLevel;
import org.example.validation.ValidName;
import org.example.validation.ValidSpecies;

public record PetDTO(@ValidName String name, @ValidSpecies String species,
                     @ValidLevel int hungerLevel, @ValidLevel int happiness) {

    public PetDTO feed() {
        if (hungerLevel <= 1) {
            throw new IllegalStateException("Pet is not hungry (hunger level already at minimum)");
        }
        return new PetDTO(name, species, hungerLevel - 1, happiness);
    }

    public PetDTO play() {
        if (happiness >= 10) {
            throw new IllegalStateException("Pet is at maximum happiness (happiness level already at maximum)");
        }
        return new PetDTO(name, species, hungerLevel, happiness + 1);
    }
}
