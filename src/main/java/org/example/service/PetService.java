package org.example.service;

import org.example.dto.PetDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.*;

@ApplicationScoped
public class PetService {
    private final Map<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong();

    public PetDTO addPet(PetDTO pet) {
        long id = idGen.incrementAndGet();
        pet.setId(id);
        pets.put(id, pet);
        return pet;
    }

    public Collection<PetDTO> getAll() {
        return pets.values();
    }

    public PetDTO getById(Long id) {
        return pets.get(id);
    }

    public void delete(Long id) {
        pets.remove(id);
    }
}
