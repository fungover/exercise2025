package org.example.service.impl;

import org.example.dto.PetDTO;
import org.example.service.api.PetService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadSafePetService implements PetService {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();

    @Override
    public PetDTO addPet(PetDTO petDTO) {
        return null;
    }

    @Override
    public List<PetDTO> getAllPets() {
        return List.of();
    }

    @Override
    public PetDTO getPetById(Long id) {
        return null;
    }

    @Override
    public PetDTO feedPet(Long id) {
        return null;
    }

    @Override
    public PetDTO playWithPet(Long id) {
        return null;
    }

    @Override
    public PetDTO deletePet(Long id) {
        return null;
    }
}
