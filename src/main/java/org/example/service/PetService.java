package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.dto.PetDTO;
import org.example.repository.PetRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped // CDI: one instance of this service will be created and reused
public class PetService {

    @Inject
    private PetRepository petRepository; // CDI: injecting the repository for storage operations.

    private final ReentrantLock lock = new ReentrantLock();

    public PetDTO createPet(PetDTO pet) {
        return petRepository.save(pet); // Save the pet using the repository
    }

    public Collection<PetDTO> getAllPets() {
        return petRepository.findAll(); // Retrieve all pets from the repository (This was used before filtering, sorting and pagination)
    }

    public Optional<PetDTO> getPetById(Long id) {
        return petRepository.findById(id); // Retrieve a pet by its ID
    }

    public boolean deletePet(Long id) {
        return petRepository.delete(id); // Delete a pet by its ID
    }

    public Optional<PetDTO> feedPet(Long id) {
        lock.lock(); // Lock here to ensure thread safety when updating pet state.
        try {
            return petRepository.findById(id).map(pet -> {
                int newHunger = Math.max(0, pet.getHungerLevel() - 10); // Decrease hunger level but not below 0
                pet.setHungerLevel(newHunger);
                return petRepository.update(pet); // Update the pet in the repository
            });
        } finally {
            lock.unlock(); // Unlock so other threads can proceed.
        }
    }

    public Optional<PetDTO> playWithPet(Long id) {
        lock.lock();
        try {
            return petRepository.findById(id).map(pet -> {
                int newHappiness = Math.min(100, pet.getHappiness() + 10); // Increase happiness but not above 100
                pet.setHappiness(newHappiness);
                return petRepository.update(pet); // Update the pet in the repository
            });
        } finally {
            lock.unlock();
        }
    }

    // Start working on filtering, sorting and pagination features here

    public List<PetDTO> searchPets(int offset, int limit, String species, String sortBy, String order) {
        Stream<PetDTO> stream = petRepository.findAll().stream(); // Start with all pets as a stream

        stream = applyFilter(stream, species); // Apply filtering if species is provided
        stream = applySorting(stream, sortBy, order); // Apply sorting based on sortBy and order parameters
        return applyPagination(stream, offset, limit); // Apply pagination and collect the results into a list
    }

    private Stream<PetDTO> applyFilter(Stream<PetDTO> stream, String species) {
        if (species != null && !species.isBlank()) {
            return stream.filter(p -> p.getSpecies().equalsIgnoreCase(species)); // Filter by species if provided
        }
        return stream;
    }

    private Stream<PetDTO> applySorting(Stream<PetDTO> stream, String sortBy, String order) {
        Comparator<PetDTO> comparator = switch (sortBy.toLowerCase()) { // Determine comparator based on sortBy parameter
            case "name" ->
                    Comparator.comparing(PetDTO::getName, String.CASE_INSENSITIVE_ORDER); // Case-insensitive sorting for names
            case "species" ->
                    Comparator.comparing(PetDTO::getSpecies, String.CASE_INSENSITIVE_ORDER); // Case-insensitive sorting for species
            case "happiness" -> Comparator.comparingInt(PetDTO::getHappiness); // Numeric sorting for happiness
            case "hungerlevel" -> Comparator.comparingInt(PetDTO::getHungerLevel); // Numeric sorting for hunger level
            default -> Comparator.comparing(PetDTO::getId); // Default sorting by ID
        };

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed(); // Reverse the comparator for descending order
        }
        return stream.sorted(comparator); // Apply the sorting to the stream
    }

    private List<PetDTO> applyPagination(Stream<PetDTO> stream, int offset, int limit) {
        return stream
                .skip(offset) // Skip the number of elements specified by 'offset'
                .limit(limit) // Limit the result to 'limit' elements
                .collect(Collectors.toList()); // Collect the results into a list
    }
}
