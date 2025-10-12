package org.example.repository;
import org.example.animal.Animal;

import java.util.*;

public class InMemoryAnimalRepository implements AnimalRepository {
    private final List<Animal> animals = new ArrayList<>();

    @Override
    public void save(Animal animal) {
        animals.add(animal);
        System.out.println("Saved: " + animal.name());
    }
}
