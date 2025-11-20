package org.example.config;

import jakarta.transaction.Transactional;
import org.example.PetRepository;
import org.example.enteties.Pet;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationRunner {
    private final PetRepository repository;

    public DevDataInitializer(PetRepository repository) {
        this.repository = repository;
    }
    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        boolean forceInit = args.containsOption("force-init");

        if(forceInit || repository.count() == 0) {
            var pet1 = new Pet("T-rex", "Rat", 50, 80);
            var pet2 = new Pet("Chetney", "Rat", 40, 90);
            var pet3 = new Pet("Håkan Bråkan", "Rat", 80, 70);
            var pet4 = new Pet("Madstab", "rat", 70,70);
            var pet5 = new Pet("Pimpek", "rat", 70,60);
            var pet6 = new Pet("Rolf", "dog", 80, 80);
            repository.saveAll(List.of(pet1, pet2, pet3, pet4, pet5, pet6));
        }

    }
}
