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
        PetDTO updatedPet = pets.computeIfPresent(id, (k, pet) -> {
            int oldHunger = pet.getHungerLevel();
            pet.setHungerLevel(Math.max(0, oldHunger - 1));
            System.out.println("Feeding pet " + pet.getId() + ": " + oldHunger + " -> " + pet.getHungerLevel());
            return pet;
        });
        return updatedPet;
    }

    public PetDTO playWithPet(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, (k, pet) -> {
            int oldHappiness = pet.getHappiness();
            pet.setHappiness(Math.min(100, oldHappiness + 1));
            System.out.println("Playing with pet " + pet.getId() + ": " + oldHappiness + " -> " + pet.getHappiness());
            return pet;
        });
        return updatedPet;
    }

    public PetDTO removePet(long id) {
        return pets.remove(id);
    }

    public Collection<PetDTO> getAllPetsFiltered(int offset, int limit, String species, String sortBy, String order) {
        return pets.values().stream()
                .filter(pet -> species == null || pet.getSpecies().equalsIgnoreCase(species))

                .sorted((p1, p2) -> {
                    int comparison = 0;
                    switch (sortBy.toLowerCase()) {
                        case "name" -> comparison = p1.getName().compareToIgnoreCase(p2.getName());
                        case "species" -> comparison = p1.getSpecies().compareToIgnoreCase(p2.getSpecies());
                        case "happiness" -> comparison = Integer.compare(p1.getHappiness(), p2.getHappiness());
                        case "hungerlevel" -> comparison = Integer.compare(p1.getHungerLevel(), p2.getHungerLevel());
                        default -> comparison = Long.compare(p1.getId(), p2.getId());
                    }
                    return order.equalsIgnoreCase("desc") ? -comparison : comparison;
                })
                .skip(offset)
                .limit(limit)
                .toList();
    }
}
