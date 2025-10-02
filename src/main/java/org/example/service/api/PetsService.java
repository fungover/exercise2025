package org.example.service.api;

import org.example.dto.PetDTO;

import java.util.List;

public interface PetsService {
    PetDTO addPet(PetDTO petDTO);
    List<PetDTO> getAllPets();
    List<PetDTO> getAllPets(int offset, int limit);
    PetDTO getPetById(Long id);
    PetDTO feedPet(Long id);
    PetDTO playWithPet(Long id);
    PetDTO deletePet(Long id);
}
