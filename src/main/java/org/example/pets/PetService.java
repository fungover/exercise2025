package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

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
        long id = idGenerator.incrementAndGet();
        pet.setId(id);
        pets.put(id, pet);
        return pet;
    }
    public List<PetDTO> getAllPets(){
        return new ArrayList<>(pets.values());

    }
    public PetDTO getPet(long id) {
        return pets.get(id);
    }
    public void feedPet(long id){
        PetDTO pet = pets.get(id);
        if(pet == null){
            throw new NotFoundException();
        }
        var hunger = Math.max(0, pet.getHungerLevel()-10);
        pet.setHungerLevel(hunger);
    }
    public void playWithPet(long id){
        PetDTO pet = pets.get(id);
        if(pet == null){
            throw new NotFoundException();
        }
        var happiness = Math.min(100, pet.getHappiness());
        pet.setHappiness(happiness + 10);
    }
    public void deletePet(long id){
        pets.remove(id);
    }
}
