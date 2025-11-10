package org.example;

import org.example.entities.Pet;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootApplication
public class Exercise2025Application {

    void main(String[] args) {
        SpringApplication.run(Exercise2025Application.class, args);
    }

    @Bean
    @Profile("dev")
    ApplicationRunner initializeDataBaseInDev(PetRepository repository){
        return args -> {
            if(repository.count() == 0) {
                repository.saveAll(List.of(
                        new org.example.entities.Pet("Spot", "Dog"),
                        new org.example.entities.Pet("Fido", "Cat"),
                        new Pet("Quack", "Bird")
                ));
            }
        };
    }

}
