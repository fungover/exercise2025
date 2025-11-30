package org.example;

import org.example.entities.Pet;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
public class PetService {
    private final ReentrantLock lock = new ReentrantLock();
    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public Pet addPet(Pet pet) {
        repository.save(pet);
        return pet;
    }

    public Pet getPet(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void removePet(Integer id) {
        repository.removeById(id);
    }


    public Pet feedPet(Integer id) {
        lock.lock();

        try {
            Pet pet = getPet(id);

            if (pet != null) {
                pet.setHungerLevel(pet.getHungerLevel() + 10);
                return pet;
            }

            return null;
        } finally {
            lock.unlock();
        }
    }

    public Pet playPet(Integer id) {
        lock.lock();

        try {
            Pet pet = getPet(id);

            if (pet != null) {
                pet.setHappiness(pet.getHappiness() + 10);
                return pet;
            }

            return null;
        } finally {
            lock.unlock();
        }
    }
}