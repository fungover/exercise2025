package org.example.repository;
import jakarta.enterprise.context.ApplicationScoped;
import org.example.animal.Animal;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class InMemoryAnimalRepository implements AnimalRepository {
    private final List<Animal> animals = new CopyOnWriteArrayList<>();

    @Override
    public void save(Animal animal) {
        animals.add(animal);
        System.out.println("Saved: " + animal.name());
    }
}
