package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class PetService {

    private final CopyOnWriteArrayList<PetDTO> petsList = new CopyOnWriteArrayList<>();

    public PetService() {
        // Mocked data
        petsList.add(new PetDTO(1L, "Rex", "Dog", 10, 10));
        petsList.add(new PetDTO(2L, "Garfield", "Cat", 4, 1));
        petsList.add(new PetDTO(3L, "Flax", "Bird", 1, 5));
    }


    public List<PetDTO> getAllPets() {
        return petsList;
    }
}
