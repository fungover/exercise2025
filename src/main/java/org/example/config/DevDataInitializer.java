package org.example.config;

import org.example.FoodRepository;
import org.example.PetRepository;
import org.example.entities.Food;
import org.example.entities.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DevDataInitializer.class);

    private final PetRepository repository;
    private final FoodRepository foodRepository;

    public DevDataInitializer(PetRepository repository, FoodRepository foodRepository) {
        this.repository = repository;
        this.foodRepository = foodRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        boolean forceInit = args.containsOption("force-init");

        if(forceInit || repository.count() > 0) {
            log.info("Initializing dev data...");

            var food1 = new Food("Bones");
            var food2 = new Food("Meat");
            var food3 = new Food("Cream");
            var food4 = new Food("Tuna");
            var food5 = new Food("Worms");
            var food6 = new Food("Seed");
            foodRepository.saveAll(List.of(food1, food2, food3, food4, food5, food6));

            repository.saveAll(List.of(
                    new Pet("Spot", "Dog", List.of(food1, food2)),
                    new Pet("Fido", "Cat", List.of(food3, food4)),
                    new Pet("Quack", "Bird", List.of(food5, food6))
            ));
        }

    }
}
