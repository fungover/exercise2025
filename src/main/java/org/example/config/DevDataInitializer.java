package org.example.config;

import jakarta.transaction.Transactional;
import org.example.entities.Catch;
import org.example.repository.CatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
//
//@Component
//@Profile("dev")
//public class DevDataInitializer implements ApplicationRunner {
//    private static final Logger log = LoggerFactory.getLogger(DevDataInitializer.class);
//    private final CatchRepository catchRepository;
//
//    public DevDataInitializer(CatchRepository catchRepository) {
//        this.catchRepository = catchRepository;
//    }
//
//    @Override
//    @Transactional
//    public void run(ApplicationArguments args) {
//        boolean forceInit = args.containsOption("force-init");
//        if (forceInit || catchRepository.count() == 0) {
//            log.info("Initializing dev data");
//            catchRepository.save(new Catch("Pike", 1.2, 1.5));
//            catchRepository.save(new Catch("Goldfish", 0.5, 0.5));
//        } else {
//            log.info("Dev data already initialized");
//        }
//    }
//}
/*
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@Component
@Profile("dev")
public class DevDataInitializer {
    private static final Logger log = LoggerFactory.getLogger(DevDataInitializer.class);
    private final CatchRepository catchRepository;

    public DevDataInitializer(CatchRepository catchRepository) {
        this.catchRepository = catchRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDevData() {
        if (catchRepository.count() == 0) {
            log.info("Initializing dev data after Flyway migration");
            catchRepository.save(new Catch("Pike", 1.2, 1.5));
            catchRepository.save(new Catch("Goldfish", 0.5, 0.5));
        } else {
            log.info("Dev data already initialized");
        }
    }
}*/
