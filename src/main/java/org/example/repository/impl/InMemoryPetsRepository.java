package org.example.repository.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.dto.PetDTO;
import org.example.repository.api.PetsRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class InMemoryPetsRepository implements PetsRepository {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);


    @Override
    public PetDTO save(PetDTO pet) {
        if (pet.getId() == 0) {
            pet.setId(generateId());
        }
        pets.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public List<PetDTO> findAll() {
        return List.copyOf(pets.values());
    }

    @Override
    public Optional<PetDTO> findById(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    @Override
    public void deleteById(Long id) {
        pets.remove(id);
    }

    @Override
    public Long generateId() {
        return idGenerator.incrementAndGet();
    }
}
