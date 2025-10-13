package org.example.pets.decorator;

import org.example.pets.PetDTO;

public record FeedDecorator(PetDTO petDTO) {

    public FeedDecorator(PetDTO petDTO) {
        this.petDTO = new PetDTO(petDTO.name(), petDTO.species(),
                petDTO.hungerLevel() - 1, petDTO.happiness());
    }

    public PetDTO feed() {
        return petDTO;
    }
}
