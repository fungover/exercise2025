package org.example.pets.id;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.pets.PetDTO;
import org.example.pets.PetsService;

@ApplicationScoped
public class PetService {

    PetsService petsService;

    public PetService() {}

    @Inject
    public PetService(PetsService petsService) {
        this.petsService = petsService;
    }

    public PetDTO getPetById(int id) {
        return petsService.getPets().get(--id);
    }
}
