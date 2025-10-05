package org.example.petadoptionservice.service;

import org.example.petadoptionservice.dto.PetDTO;

import java.util.List;

public interface PetService {
    PetDTO adopt(PetDTO pet);
    List<PetDTO> listAll();
    PetDTO getById(Long id);
    PetDTO feed(Long id);
    PetDTO play(Long id);
    void release(Long id);
}
