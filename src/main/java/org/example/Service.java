package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@ApplicationScoped
public class Service {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);
    private final ReentrantLock lock = new ReentrantLock();

    // Adopts a new pet
    public PetDTO adopt(PetDTO dto) {
        long id = idGen.getAndIncrement();
        dto.setId(id);
        pets.put(id, dto);
        return dto;
    }

    // List with all pets
    public List<PetDTO> list(int offset, int limit, String species, String sortBy, String order) {
        lock.lock();
        try {
            List<PetDTO> allPets = new ArrayList<>(pets.values());


            //pagination species
            if (species != null && !species.isEmpty()) {
                allPets.removeIf(pet -> !species.equalsIgnoreCase(pet.getSpecies()));
            }

            // Pagination limit
            if (offset < 0) offset = 0;
            if (limit < 0) limit = 10;
            int end = Math.min(offset + limit, allPets.size());

            //pagination sortBy and order

            if (offset > end) {
                return Collections.emptyList();
            }

            return new ArrayList<>(allPets.subList(offset, end));
        } finally {
            lock.unlock();
        }
    }


    // Get a pet
    public PetDTO getPet(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) throw new NotFoundException("Pet not found: " + id);
        return pet;
    }

    public PetDTO feedPet(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) throw new NotFoundException("Pet not found: " + id);
        if (pet.getHungerLevel() <= 0) {
            throw new ValidationException("Pet is not hungry!");
        }

        lock.lock();
        try {
            int newHunger = Math.max(0, pet.getHungerLevel() - 10);
            pet.setHungerLevel(newHunger);
            return pet;
        } finally {
            lock.unlock();
        }
    }

    public PetDTO playWithPet(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) throw new NotFoundException("Pet not found: " + id);
        if (pet.getHappiness() >= 100) {
            throw new ValidationException("Pet is too exhausted to play!");
        }

        lock.lock();
        try {
            int newHappiness = Math.max(0, Math.min(100, pet.getHappiness() + 10));
            pet.setHappiness(newHappiness);
            return pet;
        } finally {
            lock.unlock();
        }
    }


    public PetDTO deletePet(Long id) {
        PetDTO removed = pets.remove(id);
        if (removed == null) {
            throw new NotFoundException("Pet not found: " + id);
        }
        return removed;
    }
}
