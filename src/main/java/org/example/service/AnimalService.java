package org.example.service;

import org.example.AnimalRepository;
import org.example.entity.Pet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
private final AnimalRepository savedAnimals;

    public AnimalService(AnimalRepository savedAnimals) {
        this.savedAnimals = savedAnimals;
    }

    public List<Pet> findAllAnimals() {
        return savedAnimals.findAll();
    }

    public Pet createAnimal(Pet pet) {
        if (pet.getName() == null) {
            throw new IllegalArgumentException("Name does not exist");
        }
        if (pet.getSpecies() == null) {
            throw new IllegalArgumentException("Species does not exist");
        }
        if (pet.getAge() == null) {
            throw new IllegalArgumentException("Age does not exist");
        }
        if (pet.getBirthDate() == null) {
            throw new IllegalArgumentException("Birthdate does not exist");
        }
        return savedAnimals.save(pet);
    }

    public Pet updateAnimal(Pet pet) {
//      Optional<Pet> savedPet = savedAnimals.findById(pet.getId());
        Pet existing = savedAnimals.findById(pet.getId()).orElseThrow(() -> new IllegalArgumentException("Animal with ID " + pet.getId() + " does not exist"));

        // Debug info
        System.out.println("Age: " + existing.getAge());
        System.out.println("Created at: " + existing.getCreatedAt());
        System.out.println("Name: " + existing.getName());
        System.out.println("Species: " + existing.getSpecies());
        System.out.println("Birthdate: " + existing.getBirthDate());

        if (pet.getName() != null) {
            existing.setName(pet.getName());
        }

        if (pet.getSpecies() != null) {
            existing.setSpecies(pet.getSpecies());
        }

        if (pet.getCreatedAt() != null) {
            existing.setCreatedAt(pet.getCreatedAt());
        }

       if (pet.getAge() != null) {
           existing.setAge(pet.getAge());
       }

       if (pet.getBirthDate() != null) {
           existing.setBirthDate(pet.getBirthDate());
       }

        return savedAnimals.save(existing);
    }

    public boolean deleteAnimal(Integer id) {
        Optional<Pet> pet = savedAnimals.findById(id);
        if(pet.isPresent()) {
            savedAnimals.deleteById(id);
            return true;
        }
        return false;
    }


}
