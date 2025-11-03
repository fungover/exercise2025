package org.example.service;

import org.example.entity.Animal;
import org.example.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {
    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    public void delete(Long id) {
        animalRepository.deleteById(id);
    }
    
}
