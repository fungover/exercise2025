package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.dto.PetDTO;
import org.example.repository.PetRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

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
}
