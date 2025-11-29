package org.example.config;

import jakarta.transaction.Transactional;
import org.example.CatRepository;
import org.example.FoodRepository;
import org.example.entities.Cat;
import org.example.entities.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DevDataInitializer.class);

    private final CatRepository repository;
    private final FoodRepository foodRepository;

    public DevDataInitializer(CatRepository repository, FoodRepository foodRepository) {
        this.repository = repository;
        this.foodRepository = foodRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        boolean forceInit = args.containsOption("force-init");

        if (forceInit || repository.count() == 0) {
            log.info("Initializing dev data...");

            var food1 = new Food("Salmon");
            var food2 = new Food("Cream");
            var food3 = new Food("Meat");
            var food4 = new Food("Milk");
            foodRepository.saveAll(List.of(food1, food2, food3, food4));
            System.out.println("Food 1 id= " + food1.getId());
            repository.saveAll(List.of(
                    new Cat("Sigge", 13, List.of(food1, food2)),
                    new Cat("Maja", 3, List.of(food3, food4))
            ));

            log.info("Dev data initialized.");
        } else {
            log.info("Dev data already present. Skipping initialization.");
        }
    }
}
