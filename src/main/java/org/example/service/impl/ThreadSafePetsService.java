package org.example.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import org.example.dto.PetDTO;
import org.example.repository.api.PetsRepository;
import org.example.service.api.PetsService;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@ApplicationScoped
public class ThreadSafePetsService implements PetsService {
    private final PetsRepository repository;
    private final ReentrantLock lock = new ReentrantLock();

    public ThreadSafePetsService(PetsRepository repository) {
        this.repository = repository;
    }

    @Override
    public PetDTO addPet(PetDTO petDTO) {
        return repository.save(petDTO);
    }

    @Override
    public List<PetDTO> getAllPets() {
        return repository.findAll();
    }

    @Override
    public PetDTO getPetById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with id " + id + " not found"));
    }

    @Override
    public PetDTO feedPet(Long id) {
        PetDTO petDTO = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with id " + id + " not found"));

        if (petDTO.getHungerLevel() <= 0) {
            throw new BadRequestException("Pet is already full");
        }

        petDTO.setHungerLevel(Math.max(0, petDTO.getHungerLevel() - 10));
        return repository.save(petDTO);
    }

    @Override
    public PetDTO playWithPet(Long id) {
        PetDTO petDTO = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with id " + id + " not found"));

        if (petDTO.getHappiness() >= 100) {
            throw new BadRequestException("Pet is already happy");
        }

        petDTO.setHappiness(Math.min(100, petDTO.getHappiness() + 10));
        return repository.save(petDTO);
    }

    @Override
    public PetDTO deletePet(Long id) {
        PetDTO petDTO = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet with id " + id + " not found"));

        repository.deleteById(id);
        return petDTO;
    }
}
