package org.example.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.example.dto.PetDTO;
import org.example.service.api.PetsService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@ApplicationScoped
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
        PetDTO petDTO = pets.get(id);
        if (petDTO == null) {
            throw new NotFoundException("Pet with id " + id + " not found");
        }

        return petDTO;
    }

    @Override
    public PetDTO feedPet(Long id) {
        PetDTO petDTO = pets.get(id);
        if (petDTO == null) {
            throw new NotFoundException("Pet with id " + id + " not found");
        }

        lock.lock();
        try {
            if (petDTO.getHungerLevel() <= 0) {
                throw new BadRequestException("Pet is already full");
            }
            petDTO.setHungerLevel(Math.max(0, petDTO.getHungerLevel() - 10));
        } finally {
            lock.unlock();
        }

        return petDTO;
    }

    @Override
    public PetDTO playWithPet(Long id) {
        PetDTO petDTO = pets.get(id);
        if (petDTO == null) {
            throw new NotFoundException("Pet with id " + id + " not found");
        }

        lock.lock();
        try {
            if (petDTO.getHappiness() >= 100) {
                throw new BadRequestException("Pet is already happy");
            }
            petDTO.setHappiness(Math.min(100, petDTO.getHappiness() + 10));
        } finally {
            lock.unlock();
        }

        return petDTO;
    }

    @Override
    public PetDTO deletePet(Long id) {
        PetDTO petDTO = pets.get(id);
        if (petDTO == null) {
            throw new NotFoundException("Pet with id " + id + " not found");
        }

        lock.lock();
        try {
            pets.remove(id);
        } finally {
            lock.unlock();
        }

        return petDTO;
    }
}
