package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.example.api.PetMapper;
import org.example.domain.Pet;
import org.example.dto.PageResult;
import org.example.dto.PetDTO;
import org.example.repo.PetRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class PetService {

    PetRepository repo;

    public PetService() {
    }

    // CDI injects the repo in the app
    @Inject
    public PetService(PetRepository repo) {
        this.repo = repo;
    }

    public PetDTO adopt(PetDTO incoming) {
        Pet saved = repo.add(PetMapper.toDomainNew(incoming));
        return PetMapper.toDTO(saved);
    }

    public PetDTO get(long id) {
        Pet p = repo.findById(id).orElseThrow(() -> new NotFoundException("Pet " + id + " not found"));
        return PetMapper.toDTO(p);
    }

    public PetDTO feed(long id, int amount) {
        Pet updated = repo.update(id, p -> p.feed(amount));
        return PetMapper.toDTO(updated);
    }

    public PetDTO play(long id, int amount) {
        Pet updated = repo.update(id, p -> p.play(amount));
        return PetMapper.toDTO(updated);
    }

    public void release(long id) {
        if (!repo.delete(id)) throw new NotFoundException("Pet " + id + " not found");
    }

    // in PetService

    public PageResult<PetDTO> list(String species,
                                   String sortBy, String order,
                                   int offset, int limit) {

        List<Pet> all = repo.findAll();

        List<Pet> filtered = filterBySpecies(all, species);
        List<Pet> sorted = sortPets(filtered, sortBy, order);

        int total = sorted.size();
        var window = paginateWindow(total, offset, limit); // [from,to)

        List<PetDTO> page = sorted.subList(window.from, window.to).stream()
                .map(PetMapper::toDTO)
                .toList();

        return new PageResult<>(page, total);
    }

// ---------- helpers ----------

    private List<Pet> filterBySpecies(List<Pet> pets, String species) {
        if (species == null || species.isBlank()) return pets;
        String wanted = species.trim().toLowerCase(Locale.ROOT);
        return pets.stream()
                .filter(p -> p.species() != null
                        && p.species().toLowerCase(Locale.ROOT).equals(wanted))
                .toList();
    }

    private List<Pet> sortPets(List<Pet> pets, String sortBy, String order) {
        Comparator<Pet> cmp = comparatorFor(sortBy);
        if ("desc".equalsIgnoreCase(order)) cmp = cmp.reversed();
        return pets.stream().sorted(cmp).toList();
    }

    private Window paginateWindow(int total, int offset, int limit) {
        int from = Math.max(0, offset);
        int to = (limit > 0) ? Math.min(total, from + limit) : total;
        if (from > to) from = to; // empty window if offset beyond end
        return new Window(from, to);
    }

    /**
     * Default comparator by id if unknown field name.
     */
    private Comparator<Pet> comparatorFor(String sortBy) {
        String key = (sortBy == null ? "id" : sortBy.trim()).toLowerCase(Locale.ROOT);
        return switch (key) {
            case "name" -> Comparator.comparing(Pet::name,
                    Comparator.nullsLast(String::compareToIgnoreCase));
            case "species" -> Comparator.comparing(Pet::species,
                    Comparator.nullsLast(String::compareToIgnoreCase));
            case "hungerlevel" -> Comparator.comparingInt(Pet::hungerLevel);
            case "happiness" -> Comparator.comparingInt(Pet::happiness);
            case "id", "" -> Comparator.comparingLong(Pet::id);
            default -> Comparator.comparingLong(Pet::id); // default
        };
    }

    private record Window(int from, int to) {
    }

}
