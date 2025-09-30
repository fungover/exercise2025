package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.dto.PetDTO;
import org.example.repository.PetRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class PetService {

    @Inject
    private PetRepository petRepository;

    private final ReentrantLock lock = new ReentrantLock();

    public PetDTO createPet(PetDTO pet) {
        return petRepository.save(pet);
    }

    public Collection<PetDTO> getAllPets() {
        return petRepository.findAll();
    }

    public Optional<PetDTO> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public boolean deletePet(Long id) {
        return petRepository.delete(id);
    }

    public Optional<PetDTO> feedPet(Long id) {
        lock.lock();
        try {
            return petRepository.findById(id).map(pet -> {
                int newHunger = Math.max(0, pet.getHungerLevel() - 10);
                pet.setHungerLevel(newHunger);
                return petRepository.update(pet);
            });
        } finally {
            lock.unlock();
        }
    }

    public Optional<PetDTO> playWithPet(Long id) {
        lock.lock();
        try {
            return petRepository.findById(id).map(pet -> {
                int newHappiness = Math.min(100, pet.getHappiness() + 10);
                pet.setHappiness(newHappiness);
                return petRepository.update(pet);
            });
        } finally {
            lock.unlock();
        }
    }

    // Start working on filtering, sorting and pagination features here

    public List<PetDTO> searchPets(int offset, int limit, String species, String sortBy, String order) {
        Stream<PetDTO> stream = petRepository.findAll().stream();

        stream = applyFilter(stream, species);
        stream = applySorting(stream, sortBy, order);
        return applyPagination(stream, offset, limit);
    }

    private Stream<PetDTO> applyFilter(Stream<PetDTO> stream, String species) {
        if (species != null && !species.isBlank()) {
            return stream.filter(p -> p.getSpecies().equalsIgnoreCase(species));
        }
        return stream;
    }

    private Stream<PetDTO> applySorting(Stream<PetDTO> stream, String sortBy, String order) {
        Comparator<PetDTO> comparator = switch (sortBy.toLowerCase()) {
            case "name" -> Comparator.comparing(PetDTO::getName, String.CASE_INSENSITIVE_ORDER);
            case "species" -> Comparator.comparing(PetDTO::getSpecies, String.CASE_INSENSITIVE_ORDER);
            case "happiness" -> Comparator.comparingInt(PetDTO::getHappiness);
            case "hungerlevel" -> Comparator.comparingInt(PetDTO::getHungerLevel);
            default -> Comparator.comparing(PetDTO::getId);
        };

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        return stream.sorted(comparator);
    }

    private List<PetDTO> applyPagination(Stream<PetDTO> stream, int offset, int limit) {
        return stream
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }
}
