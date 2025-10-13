package org.fungover.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.fungover.dto.PetDTO;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PetService {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);


    public PetDTO adopt(PetDTO req) {
        long id = counter.getAndIncrement();
        PetDTO pet = new PetDTO();
        pet.setId(id);
        pet.setName(req.getName());
        pet.setSpecies(req.getSpecies());
        pet.setHappiness(req.getHappiness());
        pet.setHungerLevel(req.getHungerLevel());
        pets.put(id, pet);
        return pet;
    }

    public PetDTO get(long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) {
            throw new NoSuchElementException("Pet with id " + id + " not found");
        }
        return pet;
    }

    public void release(long id) {
        if (pets.remove(id) == null) {
            throw new NoSuchElementException("Pet with id " + id + " not found");
        }
    }
}
