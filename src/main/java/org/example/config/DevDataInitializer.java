package org.example.config;

import jakarta.transaction.Transactional;
import org.example.SkateboardRepository;
import org.example.entities.Skateboard;
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

    private final SkateboardRepository repository;

    public DevDataInitializer(SkateboardRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        boolean forceInit = args.containsOption("force-init");

        if (forceInit || repository.count() == 0) {
            log.info("Initializing dev data...");

            repository.saveAll(List.of(
                    new Skateboard("Zero", 8.0),
                    new Skateboard("Polar", 8.5)
            ));

            log.info("Done initializing dev data.");
        } else {
            log.info("Data already present. Skipping initialization.");
        }
    }
}
