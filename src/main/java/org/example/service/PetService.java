package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.dto.PetDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service layer - with all the logic for pet handling
 * Used by REST-API resources
 **/

@ApplicationScoped
/// Instance is shared between all requests
public class PetService {

    /// Thread safe map (id -> pet) instead of DB
    private final Map<Long, PetDto> pets = new ConcurrentHashMap<>();

    /// Counter to provide each animal a unique ID
    private final AtomicLong idCounter = new AtomicLong();

    /**
     * Adopts a new pet - creates an object, saves to the list
     **/
    public PetDto adoptPet(PetDto pet) {
        long id = idCounter.incrementAndGet();
        pet.setId(id);
        pets.put(id, pet);
        return pet;
    }

    /**
     * Gets all pets
     **/
    public Map<Long, PetDto> getAllPets() {
        return pets;
    }

    /**
     * Gets one specific pet based on ID
     **/
    public PetDto getPetById(Long id) {
        return pets.get(id);
    }

    /**
     * Feeds one animal -> decreases hunger level, not bellow 0
     **/
    public PetDto feedPet(Long id) {
        PetDto pet = pets.get(id);
        if (pet != null) {
            int newHunger = Math.max(0, pet.getHungerLevel() - 10);
            pet.setHungerLevel(newHunger);
        }
        return pet;
    }

    /**
     * Plays with one animal -> increases happiness level, not above 100
     **/
    public PetDto playWithPet(Long id) {
        PetDto pet = pets.get(id);
        if (pet != null) {
            int newHappiness = Math.min(100, pet.getHappiness() + 10);
            pet.setHappiness(newHappiness);
        }
        return pet;
    }

    /**
     * Releases one animal -> Deletes from list
     **/
    public boolean releasePet(Long id) {
        return pets.remove(id) != null;
    }

}
