package org.example.config;

import org.example.entities.Movie;
import org.example.repos.MovieRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;

import java.util.List;

@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DevDataInitializer.class);

    MovieRepository movieRepository;

    public DevDataInitializer(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (movieRepository.count() == 0) {
            log.info("DB empty and profile set to dev initializing dev data...");
            movieRepository.saveAll(List.of(
                    new Movie("Made up", "fantasy", "just something i made up", 2020, 90),
                    new Movie("Made up 2", "fantasy", "just something i made up", 2022, 100)
            ));
        }
    }
}
