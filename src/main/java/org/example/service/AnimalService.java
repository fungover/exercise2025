package org.example.service;

import org.example.AnimalRepository;
import org.example.entity.Pet;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return savedAnimals.save(pet);
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
