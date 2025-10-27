package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import org.example.dto.PetDTO;
import jakarta.ws.rs.BadRequestException;
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
        if (dto == null) {
            throw new BadRequestException("Request body is required");
        }
        long id = seq.getAndIncrement();
        PetDTO toStore = new PetDTO(id, dto.getName(), dto.getSpecies(), dto.getHungerLevel(), dto.getHappiness());
        store.put(id, toStore);
        return copy(toStore);
    }

    public List<PetDTO> findAll() {
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
        if (amount<0){
            throw new BadRequestException("amount must be >= 0");
        }
        int delta = amount;
        PetDTO updated = store.compute(id, (k, pet) -> {
            if (pet == null) throw notFound(id);
            pet.setHungerLevel(clamp(pet.getHungerLevel() - delta, 0, 100));
            return pet;
        });
        return copy(updated);
    }

    public PetDTO play(long id, int amount) {
        if(amount<0){
            throw new BadRequestException("amount must be >= 0");
        }
        int delta = amount;
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

    public List<PetDTO> search(Integer offset, Integer limit, String species, String sortBy, String order) {
        int off = (offset != null && offset >= 0) ? offset : 0;
        int lim = (limit != null && limit > 0 && limit <= 1000) ? limit : 50;
        String sort = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy.toLowerCase();
        boolean desc = "desc".equalsIgnoreCase(order);


        Comparator<PetDTO> cmp = switch (sort) {
            case "name" -> Comparator.comparing(PetDTO::getName, Comparator.nullsLast(String::compareToIgnoreCase));
            case "species" -> Comparator.comparing(PetDTO::getSpecies, Comparator.nullsLast(String::compareToIgnoreCase));
            case "hunger", "hungerlevel" -> Comparator.comparingInt(PetDTO::getHungerLevel);
            case "happiness" -> Comparator.comparingInt(PetDTO::getHappiness);
            default -> Comparator.comparing(PetDTO::getId);
        };
        if (desc) cmp = cmp.reversed();

        return store.values().stream()
                .filter(p -> species == null || species.isBlank()
                        || (p.getSpecies() != null && p.getSpecies().equalsIgnoreCase(species)))
                .sorted(cmp)
                .skip(off)
                .limit(lim)
                .map(PetService::copy)
                .toList();
    }
}
