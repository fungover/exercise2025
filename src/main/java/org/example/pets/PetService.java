package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PetService {

    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(4);

    public PetService() {
        // Mocked data
        pets.put(1L, new PetDTO(1L, "Rex", "Dog", 6, 10));
        pets.put(2L, new PetDTO(2L, "Garfield", "Cat", 10, 1));
        pets.put(3L, new PetDTO(3L, "Flax", "Bird", 1, 5));
    }


    public List<PetDTO> getAllPets() {
        // Convert Map to List
        return new ArrayList<>(pets.values());
    }

    public PetDTO getPetById(Long id) {
        return pets.get(id);
    }
}
