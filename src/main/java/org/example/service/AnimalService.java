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
        return savedAnimals.save(pet);
    }

    public Pet updateAnimal(Pet pet) {
        return savedAnimals.save(pet);
    }

    public void deleteAnimal(Integer id) {
        savedAnimals.deleteById(id);
    }


}
