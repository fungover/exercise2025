package org.example;

import org.example.animal.Animal;
import org.example.repository.AnimalRepository;
import org.example.repository.InMemoryAnimalRepository;
import org.example.service.AnimalService;
import org.example.service.ZooService;

public class App {
    public static void main(String[] args) {
        // Part 1
        AnimalRepository repository = new InMemoryAnimalRepository();

        AnimalService service = new ZooService(repository);

        Animal giraffe = new Animal("Longneck", "giraffe");

        service.registerAnimal(giraffe);

        // Part 2 simple container

        SimpleContainer container = new SimpleContainer();

        container.bind(AnimalRepository.class, InMemoryAnimalRepository.class);

        ZooService zooService = container.getInstance(ZooService.class);

        Animal pig = new Animal("Greta", "pig");

        zooService.registerAnimal(pig);
    }
}
