package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/*
Create a thread-safe service class to manage Pets:
Use ConcurrentHashMap<Long, PetDTO> or CopyOnWriteArrayList
Optionally use ReentrantLock for atomic updates (e.g., feeding or playing)
*/

@ApplicationScoped
public class PetService {
    private final Map<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    // Adopt pet
    public PetDTO addPet(PetDTO pet) {
        long id = nextId.getAndIncrement();
        pet.setId(id);
        pets.put(id, pet);
        return pet;
    }

    // List all pets
    public List<PetDTO> getAllPets() {
        return new ArrayList<>(pets.values());
    }

    // Get pet after id
    public PetDTO getPetById(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) {
            throw new NotFoundException("Pet with id " + id + " not found");
        }
        return pet;
    }

    // feed the chosen pet
    public PetDTO feedPet(Long id, int amount) {
        PetDTO pet = getPetById(id);
        int newHunger = Math.max(0, pet.getHungerLevel() - amount);
        pet.setHungerLevel(newHunger);
        return pet;
    }

    //play with pet
    public PetDTO playWithPet(Long id, int amount) {
        PetDTO pet = getPetById(id);
        int newHappiness = Math.min(100, pet.getHappiness() + amount);
        pet.setHappiness(newHappiness);
        return pet;
    }

}
