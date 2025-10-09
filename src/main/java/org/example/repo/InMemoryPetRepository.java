package org.example.repo;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.domain.Pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class InMemoryPetRepository implements PetRepository {

    private final ConcurrentHashMap<Long, Pet> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    @Override
    public Pet add(Pet newPetWithoutId) {
        long id = seq.getAndIncrement();
        Pet saved = new Pet(id, newPetWithoutId.name(), newPetWithoutId.species(),
                newPetWithoutId.hungerLevel(), newPetWithoutId.happiness());
        store.put(id, saved);
        return saved;
    }

    @Override
    public List<Pet> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Pet> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    }