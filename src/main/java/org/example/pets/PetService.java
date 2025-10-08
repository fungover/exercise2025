package org.example.pets;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class PetService {

    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(4);

    public PetService() {
        // Mocked data
        pets.put(1L, new PetDTO(1L, "Rex", "Dog", 6, 10));
        pets.put(2L, new PetDTO(2L, "Garfield", "Cat", 10, 1));
        pets.put(3L, new PetDTO(3L, "Flax", "Bird", 1, 5));
    }


    public List<PetDTO> getAllPets() {
        // Convert Map to List
        return new ArrayList<>(pets.values());
    }

    public PetDTO getPetById(Long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
        return pet;
    }

    public PetDTO addPet(PetDTO petDTO) {
        Long newId = idGenerator.getAndIncrement();
        petDTO.setId(newId);

        pets.put(newId, petDTO);
        return petDTO;
    }

    public PetDTO feedPet(Long id) {
        PetDTO pet = getPetById(id);

        if (pet.getHungerLevel() == 0) {
            throw new BadRequestException(pet.getName() + " is full and cannot eat more right now!");
        }

        int newHungerLevel = Math.max(0, pet.getHungerLevel() - 1);
        pet.setHungerLevel(newHungerLevel);

        return pet;
    }

    public PetDTO playWithPet(Long id) {
        PetDTO pet = getPetById(id);

        if (pet.getHappinessLevel() == 10) {
            throw new BadRequestException(pet.getName() + " is exhausted and must take a break!");
        }

        int newHappinessLevel = Math.min(10, pet.getHappinessLevel() + 1);
        pet.setHappinessLevel(newHappinessLevel);

        return pet;
    }

    public PetDTO deletePet(Long id) {
        PetDTO pet = getPetById(id);

        pets.remove(id);
        return pet;
    }

}
