// src/main/java/org/example/config/DevDataLoader.java
package org.example.config;

import org.example.model.Recipe;
import org.example.model.RecipeItem;
import org.example.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevDataLoader {

    @Bean
    CommandLineRunner loadTestData(RecipeRepository recipes) {
        return args -> {
            if (recipes.count() > 0) return;

            // Recipe 1
            Recipe tacos = Recipe.builder()
                    .title("Tacos")
                    .instructions("Use a good knife")
                    .addItem(RecipeItem.builder().name("Meat").amount(500.0).unit("g").build())
                    .addItem(RecipeItem.builder().name("Tortillas").amount(8.0).unit("pcs").build())
                    .addItem(RecipeItem.builder().name("Onion").amount(1.0).unit("pcs").build())
                    .build();

            // Recipe 2
            Recipe pasta = Recipe.builder()
                    .title("Pasta")
                    .instructions("Boil water, salt it, cook pasta al dente.")
                    .addItem(RecipeItem.builder().name("Pasta").amount(200.0).unit("g").build())
                    .addItem(RecipeItem.builder().name("Salt").amount(1.0).unit("tsp").build())
                    .build();

            recipes.save(tacos);
            recipes.save(pasta);
        };
    }
}
