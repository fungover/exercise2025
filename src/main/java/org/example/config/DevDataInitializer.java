package org.example.config;

import org.example.AnimalRepository;
import org.example.entity.Pet;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

// One way to initialize the database in dev profile
@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationRunner {

    AnimalRepository savedAnimals;

    public DevDataInitializer(AnimalRepository savedAnimals) {
        this.savedAnimals = savedAnimals;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (savedAnimals.count() == 0) {
            savedAnimals.saveAll(List.of(
            new Pet("Budgie", "Kevin", 1, "2024-12-01 12:00:00"),
            new Pet("Budgie", "Bob", 1, "2024-12-01 12:00:00"),
            new Pet("Hamster", "Fabian", 0, "2025-10-14 12:00:00")
            ));

        }



    }
}
