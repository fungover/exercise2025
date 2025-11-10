package org.example;

import org.example.entities.Food;
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
    ApplicationRunner initializeDataBaseInDev(PetRepository repository, FoodRepository foodRepository){
        return args -> {
            if(repository.count() == 0) {
                var food1 = new Food("Bones");
                var food2 = new Food("Meat");
                var food3 = new Food("Cream");
                var food4 = new Food("Cat milk");
                var food5 = new Food("Worms");
                var food6 = new Food("Seed");
                foodRepository.saveAll(List.of(food1, food2, food3, food4, food5, food6));


                repository.saveAll(List.of(

                        new org.example.entities.Pet("Spot", "Dog", List.of(food1, food2)),
                        new org.example.entities.Pet("Fido", "Cat", List.of(food3, food4)),
                        new Pet("Quack", "Bird", List.of(food5, food6))
                ));
            }
        };
    }

}
