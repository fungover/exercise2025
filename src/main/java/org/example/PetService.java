package org.example;

public class PetService {
    public static PetDTO feedPet(PetDTO pet) {
        System.out.println("In service.feedPet()");
        var hunger = pet.getHunger()-10;
        System.out.println(pet.getName());
        return new PetDTO(pet.getName(), pet.getSpecies(), hunger, pet.getHappiness(), pet.getId());
    }
    public static PetDTO playWithPet(PetDTO pet) {
        System.out.println("in service.playWithPet()");
        var happiness = pet.getHappiness()+10;
        System.out.println(pet.getName());
        return new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), happiness, pet.getId());
    }
}
