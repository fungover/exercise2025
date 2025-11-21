package org.example.config;

import jakarta.transaction.Transactional;
import org.example.entities.TruckBrand;
import org.example.entities.TruckSize;
import org.example.repositories.SkateboardRepository;
import org.example.entities.Skateboard;
import org.example.repositories.TruckBrandRepository;
import org.example.repositories.TruckSizeRepository;
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

    private final SkateboardRepository skateboardRepository;
    private final TruckBrandRepository truckBrandRepository;
    private final TruckSizeRepository truckSizeRepository;

    public DevDataInitializer(SkateboardRepository sr, TruckBrandRepository tbr,
                              TruckSizeRepository tsr) {
        this.skateboardRepository = sr;
        this.truckBrandRepository = tbr;
        this.truckSizeRepository = tsr;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        boolean forceInit = args.containsOption("force-init");

        if (forceInit || skateboardRepository.count() == 0) {
            log.info("Initializing dev data...");

            skateboardRepository.saveAll(List.of(
                    new Skateboard("ZERO", 8.0),
                    new Skateboard("POLAR", 8.5),
                    new Skateboard("AntiHero", 9.0)
            ));

            var ace = new TruckBrand("ACE");
            var independent = new TruckBrand("Independent");
            var thunder = new TruckBrand("Thunder");

            truckBrandRepository.saveAll(List.of(
                    ace,
                    independent,
                    thunder
            ));

            // The sizing is a bit simplified than in reality...
            truckSizeRepository.saveAll(List.of(
                    new TruckSize(33, 8.0, ace.getId()),
                    new TruckSize(55, 8.5, ace.getId()),
                    new TruckSize(66, 9.0, ace.getId()),

                    new TruckSize(139, 8.0, independent.getId()),
                    new TruckSize(149, 8.5, independent.getId()),
                    new TruckSize(169, 9.0, independent.getId()),

                    new TruckSize(147, 8.0, thunder.getId()),
                    new TruckSize(149, 8.5, thunder.getId()),
                    new TruckSize(161, 9.0, thunder.getId())
            ));

            log.info("Done initializing dev data.");
        } else {
            log.info("Data already present. Skipping initialization.");
        }
    }
}
