package org.example.repository;

import org.example.dto.PetDTO;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryPetRepository implements PetRepository {

    private final Map<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public PetDTO save(PetDTO pet) {
        Long id = idGenerator.getAndIncrement();
        pet.setId(id);
        pets.put(id, pet);
        return pet;
    }

    @Override
    public Collection<PetDTO> findAll() {
        return pets.values();
    }

    @Override
    public Optional<PetDTO> findById(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    @Override
    public boolean delete(Long id) {
        return pets.remove(id) != null;
    }

    @Override
    public PetDTO update(PetDTO pet) {
        pets.put(pet.getId(), pet);
        return pet;
    }
}
