package org.example.petadoptionservice.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Validator;
import org.example.petadoptionservice.dto.PetDTO;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@ApplicationScoped
public class PetServiceImpl implements PetService {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final ReentrantLock lock = new ReentrantLock();

    @Inject
    private Validator validator;

    @Override
    public PetDTO adopt(PetDTO pet) {
        validator.validate(pet);
        Long id = idGenerator.getAndIncrement();
        PetDTO newPet = new PetDTO(id, pet.name(), pet.species(), pet.hungerLevel(), pet.happiness());
        pets.put(id, newPet);
        return newPet;
    }

    @Override
    public List<PetDTO> listAll() {
        return List.copyOf(pets.values());
    }

    @Override
    public PetDTO getById(Long id) {
        return pets.get(id);
    }

    @Override
    public PetDTO feed(Long id) {
        lock.lock();
        try {
            PetDTO pet = getById(id);
            int newHungerLevel = Math.max(0, pet.hungerLevel() - 10);
            PetDTO updated = new PetDTO(id, pet.name(), pet.species(), newHungerLevel, pet.happiness());
            pets.put(id, updated);
            return updated;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public PetDTO play(Long id) {
        lock.lock();
        try {
            PetDTO pet = getById(id);
            int newHappinessLevel = Math.min(100, pet.happiness() + 10);
            int newHunger = Math.min(100, pet.hungerLevel() + 10 );
            PetDTO updated = new PetDTO(id, pet.name(), pet.species(), newHunger, newHappinessLevel);
            pets.put(id, updated);
            return updated;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void release(Long id) {
        if (pets.remove(id) == null) {
            throw new IllegalArgumentException("Pet with id " + id + " does not exist");
        }
    }

}
