package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.dto.PetDTO;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Thread,safe service for managing Pet entities.
 */
@ApplicationScoped
public class PetService {

    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ReentrantLock lock = new ReentrantLock();


    public PetDTO adoptPet(PetDTO pet) {
        lock.lock();
        try {
            Long id = idGenerator.getAndIncrement();
            // Create a copy to store internally
            PetDTO internalPet = new PetDTO(pet);
            internalPet.setId(id);
            pets.put(id, internalPet);
            // Return a defensive copy
            return new PetDTO(internalPet);
        } finally {
            lock.unlock();
        }
    }

    // -- get all the pets--

    public List<PetDTO> getAllPets() {
        return pets.values().stream()
                .map(PetDTO::new)
                .collect(Collectors.toList());
    }

    // -- get pets by id--
    public Optional<PetDTO> getPetById(Long id) {
        PetDTO pet = pets.get(id);
        return pet != null ? Optional.of(new PetDTO(pet)) : Optional.empty();
    }

    // -- reduse hunger lever---
    public Optional<PetDTO> feedPet(Long id, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        PetDTO updated = pets.computeIfPresent(id, (k, existing) -> {
            PetDTO copy = new PetDTO(existing);
            copy.setHungerLevel(Math.max(0, existing.getHungerLevel() - amount));
            return copy;
        });
        return Optional.ofNullable(updated).map(PetDTO::new);
    }

    //-- play with pet--
    public Optional<PetDTO> playWithPet(Long id, int amount) {
        // Input validation
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }

        lock.lock();
        try {
            PetDTO pet = pets.get(id);
            if (pet == null) {
                return Optional.empty();
            }

            int newHappiness = Math.min(100, pet.getHappiness() + amount);
            pet.setHappiness(newHappiness);
            return Optional.of(new PetDTO(pet));
        } finally {
            lock.unlock();
        }
    }

    // -- relase a pet--
    public boolean releasePet(Long id) {
        lock.lock();
        try {
            return pets.remove(id) != null;
        } finally {
            lock.unlock();
        }
    }

    // --get pet with pagination---
    public List<PetDTO> getPetsWithPagination(int offset, int limit) {
        // Validate pagination parameters
        if (offset < 0 || limit < 0) {
            throw new IllegalArgumentException("Offset and limit must be non-negative");
        }

        return pets.values().stream()
                .skip(offset)
                .limit(limit)
                .map(PetDTO::new)
                .collect(Collectors.toList());
    }

    // --Get pets filtered by species--
    public List<PetDTO> getPetsBySpecies(String species) {
        // Handle null species parameter
        if (species == null) {
            return Collections.emptyList();
        }

        return pets.values().stream()
                .filter(pet -> species.equalsIgnoreCase(pet.getSpecies()))
                .map(PetDTO::new)
                .collect(Collectors.toList());
    }

    // --Get pets with sorting--
    public List<PetDTO> getPetsWithSorting(String sortBy, String order) {
        // --Validate and set default for sortBy--
        if (sortBy == null || sortBy.trim().isEmpty()) {
            sortBy = "id";
        }
        if (order == null) {
            order = "asc";
        }

        Comparator<PetDTO> comparator = getComparator(sortBy);
        if (comparator == null) {
            comparator = Comparator.comparing(PetDTO::getId);
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return pets.values().stream()
                .sorted(comparator)
                .map(PetDTO::new)
                .collect(Collectors.toList());
    }

    // Get pets with pagination, filtering, and sorting--
    public List<PetDTO> getPetsWithFilters(String species, String sortBy, String order, int offset, int limit) {
        // Validate pagination parameters
        if (offset < 0 || limit < 0) {
            throw new IllegalArgumentException("Offset and limit must be non-negative");
        }

        var stream = pets.values().stream();

        if (species != null && !species.trim().isEmpty()) {
            stream = stream.filter(pet -> species.equalsIgnoreCase(pet.getSpecies()));
        }
        if (sortBy != null && !sortBy.trim().isEmpty()) {
            Comparator<PetDTO> comparator = getComparator(sortBy);
            if ("desc".equalsIgnoreCase(order)) {
                comparator = comparator.reversed();
            }
            stream = stream.sorted(comparator);
        }

        return stream.skip(offset)
                .limit(limit)
                .map(PetDTO::new)
                .collect(Collectors.toList());
    }

    // --total pets---
    public long getPetCount() {
        return pets.size();
    }

    // --- total filtered pets-----
    public long getPetCountBySpecies(String species) {
        return pets.values().stream()
                .filter(pet -> pet.getSpecies().equalsIgnoreCase(species))
                .count();
    }

    // --helper method--
    private Comparator<PetDTO> getComparator(String sortBy) {
        return switch (sortBy.toLowerCase()) {
            case "name" -> Comparator.comparing(PetDTO::getName, String.CASE_INSENSITIVE_ORDER);
            case "species" -> Comparator.comparing(PetDTO::getSpecies, String.CASE_INSENSITIVE_ORDER);
            case "hungerlevel" -> Comparator.comparing(PetDTO::getHungerLevel);
            case "happiness" -> Comparator.comparing(PetDTO::getHappiness);
            default -> Comparator.comparing(PetDTO::getId);
        };
    }
}
