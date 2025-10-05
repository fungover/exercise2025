package org.example.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.example.dto.PetDTO;
import org.example.exception.PetStateException;
import org.example.repository.api.PetsRepository;
import org.example.service.api.PetsService;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

@ApplicationScoped
public class ThreadSafePetsService implements PetsService {
    private final PetsRepository repository;
    private final ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();

    @Inject
    public ThreadSafePetsService(PetsRepository repository) {
        this.repository = repository;
    }

    @Override
    public PetDTO addPet(PetDTO petDTO) {
        return repository.save(petDTO);
    }

    @Override
    public List<PetDTO> getAllPets(int offset, int limit, String species, String sortBy, String order) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must be greater than or equal to 0");
        }

        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be greater than 0");
        }

        Stream<PetDTO> petStream = repository.findAll().stream();

        if (species != null && !species.isBlank()) {
            petStream = petStream.filter(pet -> pet.getSpecies().equalsIgnoreCase(species));
        }

        Comparator<PetDTO> comparator;
        if ("happiness".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(PetDTO::getHappiness);
        } else {
            comparator = Comparator.comparing(PetDTO::getId);
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return petStream
                .sorted(comparator)
                .skip(offset)
                .limit(limit)
                .toList();
    }

    @Override
    public PetDTO getPetById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with id " + id + " not found"));
    }

    @Override
    public PetDTO feedPet(Long id) {
        ReentrantLock lock = getLock(id);
        lock.lock();
        try {
            PetDTO petDTO = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Pet with id " + id + " not found"));

            if (petDTO.getHungerLevel() <= 0) {
                throw new PetStateException("Pet is already full");
            }

            petDTO.setHungerLevel(Math.max(0, petDTO.getHungerLevel() - 10));
            return repository.save(petDTO);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public PetDTO playWithPet(Long id) {
        ReentrantLock lock = getLock(id);
        lock.lock();
        try {
            PetDTO petDTO = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Pet with id " + id + " not found"));

            if (petDTO.getHappiness() >= 100) {
                throw new PetStateException("Pet is already happy");
            }

            petDTO.setHappiness(Math.min(100, petDTO.getHappiness() + 10));
            return repository.save(petDTO);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public PetDTO deletePet(Long id) {
        ReentrantLock lock = getLock(id);
        lock.lock();
        try {
            PetDTO petDTO = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Pet with id " + id + " not found"));

            repository.deleteById(id);
            locks.remove(id);
            return petDTO;
        } finally {
            lock.unlock();
        }
    }

    private ReentrantLock getLock(Long id) {
        ReentrantLock lock = locks.computeIfAbsent(id, k -> new ReentrantLock());
        if (locks.size() > 1000) {
            locks.entrySet().removeIf(e -> !e.getValue().isLocked() && !e.getValue().hasQueuedThreads());
        }
        return lock;
    }

}
