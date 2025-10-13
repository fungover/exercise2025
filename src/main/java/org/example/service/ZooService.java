package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.animal.Animal;
import org.example.repository.AnimalRepository;

@ApplicationScoped
public class ZooService implements AnimalService {
    private final AnimalRepository repository;

    @Inject
    public ZooService(AnimalRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerAnimal(Animal animal) {
        System.out.println("Registered new animal " + animal.species() + " with the name " + animal.name());
        repository.save(animal);
    }
}
