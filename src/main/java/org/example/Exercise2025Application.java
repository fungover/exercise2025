package org.example;

import org.example.entity.Pet;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootApplication
public class Exercise2025Application {

   public static void main(String[] args) {
        SpringApplication.run(Exercise2025Application.class, args);
    }


// One way to initialize the database in dev profile
@Bean
@Profile("dev")
ApplicationRunner initializeDatabaseInDev(AnimalRepository savedAnimals) {
       return args -> {
           if (savedAnimals.count() == 0) {
               savedAnimals.saveAll(List.of(
               new Pet("Budgie", "Kevin", 1, "2024-12-01 12:00:00"),
               new Pet("Budgie", "Bob", 1, "2024-12-01 12:00:00"),
               new Pet("Hamster", "Fabian", 0, "2025-10-14 12:00:00")
               ));

           }
       };
}



}
