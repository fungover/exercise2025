package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.dto.PetDTO;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * PetService is an in-memory service that manages all pets.
 * It stores pets in a thread-safe map and provides methods to
 * add, retrieve, update and delete pets.
 *
 * Why we need this:
 * - PetDTO only describes what a Pet looks like.
 * - Service is responsible for storing and modifying pets.
 * - Keeps our REST resource classes clean and focused.
 */
@ApplicationScoped
public class PetService {

    // Thread-safe map to store pets (id -> PetDTO)
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();

    // Counter to generate unique Pet IDs
    private final AtomicLong idCounter = new AtomicLong(1);

    // Add (adopt) a new pet. A new ID will be assigned automatically.
    public Long addPet(PetDTO pet) {
        Long id = idCounter.getAndIncrement();
        pets.put(id, pet);
        return id;
    }

    // Get all pets
    public Collection<PetDTO> getAllPets() {
        return pets.values();
    }

    // Find a specific pet by its ID
    public PetDTO getPetById(Long id) {
        return pets.get(id);
    }

    // Delete (release) a pet by its ID
    public boolean deletePet(Long id) {
        return pets.remove(id) != null;
    }

    // Feed a pet: reduce hunger by 10 (not below 0)
    public void feedPet(Long id) {
        pets.computeIfPresent(id, (k, pet) -> {
            int newHungerLevel = Math.max(0, pet.getHungerLevel() - 10);
            pet.setHungerLevel(newHungerLevel);
            return pet;
        });
    }

    // Play with a pet: increase happiness by 10 (not above 100)
    public void playWithPet(Long id) {
        pets.computeIfPresent(id, (k, pet) -> {
            int newHappiness = Math.min(100, pet.getHappiness() + 10);
            pet.setHappiness(newHappiness);
            return pet;
        });
    }
}
