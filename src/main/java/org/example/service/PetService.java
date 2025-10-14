package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.DTO.PetDTO;

import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@ApplicationScoped
public class PetService {

    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, ReentrantLock> locks = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1L);

    public PetDTO adoptPet(PetDTO pet) {
        long id = idSequence.incrementAndGet();
        PetDTO copy = new PetDTO(id, pet.getName(), pet.getSpecies(), pet.getHungerLevel(), pet.getHappiness());
        pets.put(id, copy);
        locks.put(id, new ReentrantLock());
        return copy;
    }

    public List<PetDTO> listAllPets() {
        return List.copyOf(pets.values());
    }

    public PetDTO getPetWithID(long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) {
            throw new NotFoundException("Pet with id " + id + " not found");
        }
        return pet;
    }

    public PetDTO feedPet(long id, int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
        ReentrantLock lock = locks.get(id);
        if (lock == null) throw new NotFoundException("Pet with id " + id + " not found");

        lock.lock();
        try {
            PetDTO pet = pets.get(id);
            if (pet == null) throw new NotFoundException("Pet with id " + id + " not found");
            int newHungerLevel = pet.getHungerLevel() - amount;
            pet.setHungerLevel(newHungerLevel);
            return pet;
        } finally {
            lock.unlock();
        }
    }

    public PetDTO play(Long id, int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        ReentrantLock lock = locks.get(id);
        if (lock == null) throw new NotFoundException("Pet with id " + id + " not found");

        lock.lock();
        try {
            PetDTO pet = pets.get(id);
            if (pet == null) throw new NotFoundException("Pet with id " + id + " not found");
            int newHappiness = Math.min(100, pet.getHappiness() + amount);
            pet.setHappiness(newHappiness);
            return pet;
        } finally {
            lock.unlock();
        }
    }

    public void deletePet(long id) {
        PetDTO remove = pets.remove(id);
        ReentrantLock lock = locks.remove(id);
        if (remove == null) throw new NotFoundException("Pet with id " + id + " not found");
    }

}