package org.example.service.impl;

import org.example.dto.PetDTO;
import org.example.service.api.PetsService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadSafePetsService implements PetsService {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public PetDTO addPet(PetDTO petDTO) {
        long id = idGenerator.incrementAndGet();
        petDTO.setId(id);
        pets.put(id, petDTO);

        return petDTO;
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
