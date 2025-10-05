package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
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
        PetDTO pet = pets.get(id);
        if (pet == null) {
            throw new NotFoundException("Pet with ID " + id + " not found");
        }
        return pet;
    }

    public PetDTO addPet(PetDTO pet) {
        long id = idCounter.getAndIncrement();
        pet.setId(id);
        pets.put(id, pet);
        return pet;
    }

    public PetDTO feedPet(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, (k, pet) -> {
            pet.setHungerLevel(Math.max(0, pet.getHungerLevel() - 1));
            return pet;
        });
        if (updatedPet == null) {
            throw new NotFoundException("Pet with ID " + id + " not found");
        }
        return updatedPet;
    }

    public PetDTO playWithPet(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, (k, pet) -> {
            pet.setHappiness(Math.min(100, pet.getHappiness() + 1));
            return pet;
        });
        if (updatedPet == null) {
            throw new NotFoundException("Pet with ID " + id + " not found");
        }
        return updatedPet;
    }

    public PetDTO removePet(long id) {
        PetDTO removed = pets.remove(id);
        if (removed == null) {
            throw new NotFoundException("Pet with ID " + id + " not found");
        }
        return removed;
    }

    public Collection<PetDTO> getAllPetsFiltered(int offset, int limit, String species, String sortBy, String order) {
        return pets.values().stream()
                .filter(pet -> species == null || pet.getSpecies().equalsIgnoreCase(species))
                .sorted((p1, p2) -> {
                    int comparison;
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
