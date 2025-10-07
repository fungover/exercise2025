package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.dto.PetDTO;
import org.example.exception.NotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class PetService {
    private final ConcurrentHashMap<Long, PetDTO> store = new ConcurrentHashMap<>();
    //creates our id for our threads
    private final AtomicLong idGen = new AtomicLong(1);

    public PetDTO adoptPet(PetDTO pet) {
        long id = idGen.getAndIncrement();
        pet.setId(id);

        // ensure
        if (pet.getHungerLevel() < 0) pet.setHungerLevel(50);
        if (pet.getHappiness() < 0) pet.setHappiness(50);
        store.put(id, pet);
        return pet;
    }

    public List<PetDTO> listPets(Integer offset, Integer limit, String species,
                                 String sortBy, String order) {
        Stream<PetDTO> s = store.values()
                                .stream();

        if (species != null && !species.isBlank()) {
            s = s.filter(p -> p.getSpecies()
                               .equalsIgnoreCase(species));

        }


        if ("happiness".equalsIgnoreCase(sortBy)) {
            Comparator<PetDTO> cmp = Comparator.comparing(PetDTO::getHappiness);
            if ("desc".equalsIgnoreCase(order)) cmp = cmp.reversed();
            s = s.sorted(cmp);
        } else if ("hunger".equalsIgnoreCase(sortBy)) {
            Comparator<PetDTO> cmp = Comparator.comparingInt(PetDTO::getHungerLevel);
            if ("desc".equalsIgnoreCase(order)) cmp = cmp.reversed();
            s = s.sorted(cmp);
        }

        if (offset == null || offset < 0) offset = 0;
        if (limit == null || limit < 1) limit = Integer.MAX_VALUE;

        return s.skip(offset)
                .limit(limit)
                .collect(Collectors.toList());

    }

    public PetDTO getPet(Long id) {
        PetDTO p = store.get(id);
        if (p == null)
            throw new NotFoundException("Pet with id " + id + " not found");
        return p;
    }

    //feed: reduce hunger (no negative)
    public PetDTO feedPet(long id, int amount) {
        return store.compute(id, (k, old) -> {
            if (old == null)
                throw new NotFoundException("Pet with id " + id + " not found");
            int newHunger = Math.max(0, old.getHungerLevel() - amount);
            old.setHungerLevel(newHunger);
            return old;
        });
    }

    //play: increase happiness (cap 100)
    public PetDTO playWithPet(long id, int amount) {
        return store.compute(id, (k, old) -> {
            if (old == null)
                throw new NotFoundException("Pet with id " + id + " not found");
            int newHappiness = Math.min(100, old.getHappiness() + amount);
            old.setHappiness(newHappiness);
            return old;
        });
    }

    public void releasePet(long id) {
        if (store.remove(id) == null)
            throw new NotFoundException("Pet with id " + id + " not found");
    }

}
