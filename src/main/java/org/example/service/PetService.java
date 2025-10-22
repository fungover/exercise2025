package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import org.example.dto.PetDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PetService {

    private final ConcurrentMap<Long, PetDTO> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public PetDTO create(@Valid PetDTO dto) {
        Objects.requireNonNull(dto, "dto must not be null");
        long id = seq.getAndIncrement();
        dto.setId(id);
        store.put(id, dto);
        return dto;
    }

    public List<PetDTO> findAll() {
        // Returnera kopior i stabil ordning (id asc)
        return store.values().stream()
                .sorted(Comparator.comparing(PetDTO::getId))
                .map(PetService::copy)
                .toList();
    }

    public PetDTO findById(long id) {
        PetDTO dto = store.get(id);
        if (dto == null) throw notFound(id);
        return copy(dto);
    }

    public void delete(long id) {
        if (store.remove(id) == null) throw notFound(id);
    }

    public PetDTO feed(long id, int amount) {
        int delta = amount > 0 ? amount : 10;
        PetDTO updated = store.compute(id, (k, pet) -> {
            if (pet == null) throw notFound(id);
            pet.setHungerLevel(clamp(pet.getHungerLevel() - delta, 0, 100));
            return pet;
        });
        return copy(updated);
    }

    public PetDTO play(long id, int amount) {
        int delta = amount > 0 ? amount : 10;
        PetDTO updated = store.compute(id, (k, pet) -> {
            if (pet == null) throw notFound(id);
            pet.setHappiness(clamp(pet.getHappiness() + delta, 0, 100));
            return pet;
        });
        return copy(updated);
    }

    // ——— Helpers ———
    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }

    private static NotFoundException notFound(long id) {
        return new NotFoundException("Pet with id " + id + " not found");
    }

    private static PetDTO copy(PetDTO p) {
        if (p == null) return null;
        return new PetDTO(p.getId(), p.getName(), p.getSpecies(), p.getHungerLevel(), p.getHappiness());
    }
}
