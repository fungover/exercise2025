package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.dto.PetDTO;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PetService {

    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public Collection<PetDTO> getAllPets() {
        return pets.values();
    }

    public PetDTO getPet(long id) {
        return pets.get(id);
    }

    public PetDTO addPet(PetDTO pet) {
        long id = idCounter.getAndIncrement();
        pet.setId(id);
        pets.put(id, pet);
        return pet;
    }

    public PetDTO feedPet(long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) {
            pet.setHungerLevel(Math.max(0, pet.getHungerLevel() - 1));
        }
        return pet;
    }

    public PetDTO playWithPet(long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) {
            pet.setHappiness(pet.getHappiness() + 1);
        }
        return pet;
    }

    public PetDTO removePet(long id) {
        return pets.remove(id);
    }
}
