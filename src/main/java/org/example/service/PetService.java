package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.dto.PetDTO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Comparator;


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

    // Advanced pet retrieval with filtering, sorting and pagination
    public List<PetDTO> getPets(String species, String sortBy, String order, int offset, int limit) {

        // Convert pets from map to list for easier slicing
        var allPets = getAllPets().stream();

        // If species filter is set, only keep pets of that species
        if (species != null && !species.isBlank()) {
            allPets = allPets.filter(p -> p.getSpecies().equalsIgnoreCase(species));
        }

        // Convert to list before sorting/pagination
        var petList = allPets.toList();

        // Sorting (if sortBy is provided)
        if (sortBy != null && !sortBy.isBlank()) {
            Comparator<PetDTO> comparator = switch (sortBy.toLowerCase()) {
                case "name" -> Comparator.comparing(PetDTO::getName, String.CASE_INSENSITIVE_ORDER);
                case "species" -> Comparator.comparing(PetDTO::getSpecies, String.CASE_INSENSITIVE_ORDER);
                case "hungerlevel" -> Comparator.comparing(PetDTO::getHungerLevel);
                case "happiness" -> Comparator.comparing(PetDTO::getHappiness);
                default -> null;
            };

            if (comparator != null) {
                if ("desc".equalsIgnoreCase(order)) {
                    comparator = comparator.reversed();
                }
                petList = petList.stream().sorted(comparator).toList();
            }
        }

        // Pagination logic, calculate sublist boundaries
        int fromIndex = Math.max(0, offset);
        int toIndex = (limit < 0) ? petList.size() : Math.min(petList.size(), offset + limit);

        if (fromIndex > petList.size()) {
            return Collections.emptyList();
        }

        return petList.subList(fromIndex, toIndex);
    }

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
