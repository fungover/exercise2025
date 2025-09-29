package org.example.service.impl;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.example.dto.PetDTO;
import org.example.service.api.PetsService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafePetsService implements PetsService {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public PetDTO addPet(PetDTO petDTO) {
        long id = idGenerator.incrementAndGet();
        petDTO.setId(id);
        pets.put(id, petDTO);

        return petDTO;
    }

    @Override
    public List<PetDTO> getAllPets() {
        return List.copyOf(pets.values());
    }

    @Override
    public PetDTO getPetById(Long id) {
        if (!pets.containsKey(id)) {
            throw new NotFoundException("Pet with id " + id + " not found");
        }
        return pets.get(id);
    }

    @Override
    public PetDTO feedPet(Long id) {
        if (!pets.containsKey(id)) {
            throw new NotFoundException("Pet with id " + id + " not found");
        }

        PetDTO petDTO = pets.get(id);

        lock.lock();
        try {
            if (petDTO.getHungerLevel() >= 100) {
                throw new BadRequestException("Pet is already full");
            }
            petDTO.setHungerLevel(Math.min(100, petDTO.getHungerLevel() + 10));
        } finally {
            lock.unlock();
        }

        return petDTO;
    }

    @Override
    public PetDTO playWithPet(Long id) {
        return null;
    }

    @Override
    public PetDTO deletePet(Long id) {
        pets.remove(id);
        return null;
    }
}
