package org.example.pets.decorator;

import org.example.pets.PetDTO;

public record PlayDecorator(PetDTO petDTO) {

    public PlayDecorator(PetDTO petDTO) {
        this.petDTO = new PetDTO(petDTO.name(),  petDTO.species(),
                petDTO.hungerLevel(), petDTO.happiness() + 1);
    }

    public PetDTO play() {
        return petDTO;
    }
}
