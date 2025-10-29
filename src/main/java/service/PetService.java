package service;

import dto.PetDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PetService {
    private ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private AtomicLong idCounter = new AtomicLong(1);

    public PetDTO adoptPet(PetDTO pet){
        long id = idCounter.getAndIncrement();
        pet.setId(id);
        pets.put(id, pet);
        return pet;
    }

    public PetDTO getPet(Long id) {
        return pets.get(id);
    }

    public Collection<PetDTO> getAllPets() {
        return pets.values();
    }

    public PetDTO feedPet(Long id) {
        PetDTO pet = pets.get(id);
        if (pet != null) {
            pet.setHungerLevel(Math.max(0, pet.getHungerLevel() - 10));
        }
        return pet;
    }

    public PetDTO playWithPet(Long id) {
        PetDTO pet = pets.get(id);
        if (pet != null) {
            pet.setHappiness(Math.min(100, pet.getHappiness() + 10));
        }
        return pet;
    }

    public void releasePet(Long id) {
        pets.remove(id);
    }

}


