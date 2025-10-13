package org.example;

import org.example.animal.Animal;
import org.example.repository.AnimalRepository;
import org.example.repository.InMemoryAnimalRepository;
import org.example.service.AnimalService;
import org.example.service.ZooService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

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

        // Part 3 weld
        Weld weld = new Weld();

        try (WeldContainer weldContainer = weld.initialize()) {
            AnimalService weldService = weldContainer.select(ZooService.class).get();

            Animal goat = new Animal("Filip", "goat");
            weldService.registerAnimal(goat);
        }
    }
}
