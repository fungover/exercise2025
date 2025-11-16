package org.example.data;

import org.example.entity.Animal;
import org.example.repository.AnimalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AnimalRepository repository) {
        return args -> {
            if (repository.count() == 0) {

                Animal leo = new Animal();
                leo.setName("Leo");
                leo.setSpecies("Lion");
                leo.setAge(5);
                leo.setEnclosure("Savannah");
                leo.setEndangered(true);
                repository.save(leo);

                Animal molly = new Animal();
                molly.setName("Molly");
                molly.setSpecies("Giraffe");
                molly.setAge(7);
                molly.setEnclosure("Grassland");
                molly.setEndangered(false);
                repository.save(molly);

                Animal zara = new Animal();
                zara.setName("Zara");
                zara.setSpecies("Zebra");
                zara.setAge(4);
                zara.setEnclosure("Plains");
                zara.setEndangered(false);
                repository.save(zara);

                Animal ellie = new Animal();
                ellie.setName("Ellie");
                ellie.setSpecies("Elephant");
                ellie.setAge(12);
                ellie.setEnclosure("Forest");
                ellie.setEndangered(true);
                repository.save(ellie);

                Animal polly = new Animal();
                polly.setName("Polly");
                polly.setSpecies("Parrot");
                polly.setAge(2);
                polly.setEnclosure("Tropical House");
                polly.setEndangered(false);
                repository.save(polly);

                System.out.println(
                  "Seeded initial zoo animals into database!");
            }
        };
    }
}

