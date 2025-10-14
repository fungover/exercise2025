package org.fungover.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.fungover.dto.PetDTO;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@ApplicationScoped
public class PetService {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);


    public PetDTO adopt(PetDTO req) {
        Long id = counter.getAndIncrement();
        PetDTO pet = new PetDTO();
        pet.setId(id);
        pet.setName(req.getName());
        pet.setSpecies(req.getSpecies());
        pet.setHappiness(req.getHappiness());
        pet.setHunger(req.getHunger());
        pets.put(id, pet);
        return pet;
    }

    public List<PetDTO> getAll(
            Optional<String> species,
            Optional<Integer> offset,
            Optional<Integer> limit,
            Optional<String> sortBy,
            Optional<String> order) {

        Stream<PetDTO> stream = pets.values().stream();

        if (species.isPresent()) {
            String speciesName = species.get();
            stream = stream.filter(p -> p.getSpecies().equalsIgnoreCase(speciesName));
        }

        Comparator<PetDTO> comparator = switch (sortBy.orElse("id")) {
            case "name" -> Comparator.comparing(PetDTO::getName, String.CASE_INSENSITIVE_ORDER);
            case "species" -> Comparator.comparing(PetDTO::getSpecies, String.CASE_INSENSITIVE_ORDER);
            case "happiness" -> Comparator.comparingInt(PetDTO::getHappiness);
            case "hunger" -> Comparator.comparingInt(PetDTO::getHunger);
            default -> Comparator.comparing(p -> p.getId() == null ? Long.MAX_VALUE : p.getId());
        };

        if ("desc".equalsIgnoreCase(order.orElse("asc"))) {
            comparator = comparator.reversed();
        }

        int off = Math.max(0, offset.orElse(0));
        int lim = Math.min(100, Math.max(1, limit.orElse(50)));
        return stream.skip(off).limit(lim).toList();
    }

    public PetDTO get(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) {
            throw new NoSuchElementException("Pet with id " + id + " not found");
        }
        return pet;
    }

    public PetDTO play(Long id) {
        return pets.compute(id, (k, v) -> {
            if (v == null) throw new NoSuchElementException("Pet with id" + id + " not found");
            int newHappiness = Math.min(10, v.getHappiness() + 1);
            v.setHappiness(newHappiness);

            int newHunger = Math.min(10, v.getHunger() + 1);
            v.setHunger(newHunger);

            return v;
        });
    }

    public PetDTO feed(Long id) {
        return pets.compute(id, (k, v) -> {
            if (v == null) throw new NoSuchElementException("Pet with id " + id + " not found");
            int newHunger = Math.max(0, v.getHunger() - 1);
            v.setHunger(newHunger);

            int newHappiness = Math.min(10, v.getHappiness() + 1);
            v.setHappiness(newHappiness);

            return v;
        });
    }

    public void release(Long id) {
        if (pets.remove(id) == null) {
            throw new NoSuchElementException("Pet with id " + id + " not found");
        }
    }
}
