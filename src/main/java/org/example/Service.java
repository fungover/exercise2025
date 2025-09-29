package org.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@ApplicationScoped
public class Service{
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);
    private final ReentrantLock lock = new ReentrantLock();
    //TODO: manage hunger level
    //TODO: manage happiness level


    // Adopts a new pet
    public PetDTO adopt(PetDTO dto) {
        long id = idGen.getAndIncrement();
        dto.setId(id);
        pets.put(id, dto);
        return dto;
    }

    // List with all pets
    public List<PetDTO> list() {
        return new ArrayList<>(pets.values());
    }

    // Get a pet
    public PetDTO getPet(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) throw new NotFoundException("Pet not found: " + id);
        return pet;
    }

    public PetDTO feedPet(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) throw new NotFoundException("Pet not found: " + id);

        lock.lock();
        try {
            int newHunger = Math.max(0, pet.getHungerLevel() - 10);
            pet.setHungerLevel(newHunger);
            return pet;
        } catch (Exception e) {
            System.err.println("Error feeding pet " + id + ": " + e.getMessage());
            throw e;
        }
        finally {
            lock.unlock();
        }
    }

    public PetDTO playWithPet(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) throw new NotFoundException("Pet not found: " + id);

        lock.lock();
        try {
            int newHappiness = Math.max(0, Math.min(100, pet.getHappiness() + 10));
            pet.setHappiness(newHappiness);
            return pet;
        } catch (Exception e) {
            System.err.println("Error playing with pet " + id + ": " + e.getMessage());
            throw e;
        } finally {
            lock.unlock();
        }
    }
}
