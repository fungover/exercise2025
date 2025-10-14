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
     * Readings - gets several or one pet
     **/
    public Map<Long, PetDto> getAllPets() {
        return pets;
    }

    public PetDto getPetById(Long id) {
        return pets.get(id);
    }

    /**
     * Feeds one animal -> decreases hunger level, not bellow 0
     **/
    public PetDto feedPet(Long id, int amount) {
        int a = Math.max(0, amount);
        return pets.compute(id, (k, p) -> {
            if (p == null) return null;
            int newHunger = Math.max(0, p.getHungerLevel() - a);
            p.setHungerLevel(newHunger);
            return p;
        });
    }

    /**
     * Plays with one animal -> increases happiness level, not above 100
     **/
    public PetDto playWithPet(Long id, int amount) {
        int a = Math.max(0, amount);
        return pets.compute(id, (k, p) -> {
            if (p == null) return null;
            int newHappy = Math.min(100, p.getHappiness() + a);
            p.setHappiness(newHappy);
            return p;
        });
    }

    /**
     * Releases one animal -> Deletes from list
     **/
    public boolean releasePet(Long id) {
        return pets.remove(id) != null;
    }






    /**
     * Backup to not crash old requests
     **/
    public PetDto feedPet(Long id) {
        return feedPet(id, 10);
    }

    public PetDto playWithPet(Long id) {
        return playWithPet(id, 10);
    }

}
