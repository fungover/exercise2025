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
        PetDTO copy = new PetDTO(pet.getName(), pet.getSpecies(), pet.getHungerLevel(), pet.getHappiness());
        copy.setId(pet.getId());

        if (pet.getId() == 0) {
            copy.setId(generateId());
        }

        pets.put(copy.getId(), copy);
        return copy;
    }

    @Override
    public List<PetDTO> findAll() {
        return List.copyOf(pets.values());
    }

    @Override
    public Optional<PetDTO> findById(Long id) {
        return Optional.ofNullable(pets.get(id)).map(this::copyPet);
    }

    private PetDTO copyPet(PetDTO pet) {
        PetDTO copy = new PetDTO(pet.getName(), pet.getSpecies(), pet.getHungerLevel(), pet.getHappiness());
        copy.setId(pet.getId());
        return copy;
    }

    @Override
    public void deleteById(Long id) {
        pets.remove(id);
    }

    private Long generateId() {
        return idGenerator.incrementAndGet();
    }
}
