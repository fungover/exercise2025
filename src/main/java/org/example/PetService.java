package org.example;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class PetService {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final ReentrantLock lock = new ReentrantLock();

    public void addPet(Long id, PetDTO pet) {
        pets.put(id, pet);
    }

    public PetDTO getPet(Long id) {
        return pets.get(id);
    }

    public ConcurrentHashMap<Long, PetDTO> getPets() {
        return new ConcurrentHashMap<>(pets);
    }

    public void removePet(Long id) {
        pets.remove(id);
    }

    public boolean feedPet(Long id) {
        lock.lock();

        try {
            PetDTO pet = pets.get(id);

            if (pet == null) {
                pet.setHungerLevel(pet.getHungerLevel() + 10);
                System.out.println("You fed " + pet.getName() + ". " +  pet.getName() + " hunger is now at " + pet.getHungerLevel() + "%");
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean playPet(Long id) {
        lock.lock();
        try {
            PetDTO pet = pets.get(id);
            if (pet == null) {
                pet.setHappiness(pet.getHappiness() + 10);
                System.out.println("You played with " +  pet.getName() + ". " + pet.getName() + " is now " + pet.getHappiness() + "% happy");
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}
