package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;
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
}
