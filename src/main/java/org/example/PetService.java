package org.example;


import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class PetService {
    private static final Logger log = LoggerFactory.getLogger(PetService.class);

    public PetDTO feedPet(PetDTO pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        log.debug("Feeding pet: {}", pet.getName());
        var hunger = pet.getHunger()-10;
        if(hunger < 0){
            hunger = 0;
        }
        return new PetDTO(pet.getName(), pet.getSpecies(), hunger, pet.getHappiness(), pet.getId());
    }
    public PetDTO playWithPet(PetDTO pet) {
        if(pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        log.debug("Playing with pet: {}", pet.getName());
        var happiness = pet.getHappiness()+10;
        if(happiness < 100){
            happiness = 100;
        }
        return new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), happiness, pet.getId());
    }
}
