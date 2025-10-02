package org.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.dto.PetDTO;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped // CDI: one instance of this repository will be created and reused
public class InMemoryPetRepository implements PetRepository {

    private final Map<Long, PetDTO> pets = new ConcurrentHashMap<>(); // Thread-safe storage for pets.
    private final AtomicLong idGenerator = new AtomicLong(1); // Atomic ID generator for unique pet IDs, also thread-safe.

    @Override
    public PetDTO save(PetDTO pet) {
        Long id = idGenerator.getAndIncrement(); // Generate a new unique ID
        pet.setId(id);
        pets.put(id, pet); // Store the pet in the map
        return pet;
    }

    @Override
    public Collection<PetDTO> findAll() {
        return pets.values(); // Return all pets
    }

    @Override
    public Optional<PetDTO> findById(Long id) {
        return Optional.ofNullable(pets.get(id)); // Retrieve a pet by ID, wrapped in Optional
    }

    @Override
    public boolean delete(Long id) {
        return pets.remove(id) != null; // Remove the pet by ID, return true if it existed
    }

    @Override
    public PetDTO update(PetDTO pet) {
        pets.put(pet.getId(), pet); // Update the pet in the map, replacing the old entry
        return pet;
    }
}
