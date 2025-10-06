package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PetService {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public PetService() {
    }
    public PetDTO adoptPet(PetDTO pet) {
        pets.put(idGenerator.incrementAndGet(), pet);
        return pet;
    }
    public List<PetDTO> getAllPets(){
        return new ArrayList<>(pets.values());

    }
    public PetDTO getPet(long id) {
        return pets.get(id);
    }
    public PetDTO feedPet(long id){
        PetDTO pet = pets.get(id);
        int hunger = pet.getHungerLevel();
        pet.setHungerLevel(hunger - 10);
        return pet;
    }
    public PetDTO playWithPet(long id){
        PetDTO pet = pets.get(id);
        int happiness = pet.getHappiness();
        pet.setHappiness(happiness + 10);
        return pet;
    }
    public PetDTO deletePet(long id){
        PetDTO pet = pets.get(id);
        pets.remove(id);
        return pet;
    }
}
