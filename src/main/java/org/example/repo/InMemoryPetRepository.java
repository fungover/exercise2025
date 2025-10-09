package org.example.repo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import org.example.domain.Pet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.UnaryOperator;

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

    @Override
    public Pet update(long id, UnaryOperator<Pet> change) {
        return store.compute(id, (k, existing) -> {
            if (existing == null) throw new NotFoundException("Pet " + id + " not found");
            Pet updated = change.apply(existing);
            if (updated.id() == null || !updated.id().equals(id)) {
                updated = updated.withId(id);
            }
            return updated;
        });
    }

    @Override
    public boolean delete(long id) {
        return store.remove(id) != null;
    }
}