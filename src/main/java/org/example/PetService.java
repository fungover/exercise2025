package org.example;

import org.springframework.stereotype.Service;


@Service
public class PetService {
    public static PetDTO feedPet(PetDTO pet) {
        var hunger = pet.getHunger()-10;
        return new PetDTO(pet.getName(), pet.getSpecies(), hunger, pet.getHappiness(), pet.getId());
    }
    public static PetDTO playWithPet(PetDTO pet) {
        var happiness = pet.getHappiness()+10;
        return new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), happiness, pet.getId());
    }
}
