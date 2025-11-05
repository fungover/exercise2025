package org.example.config.security.dev;

import org.example.entities.Director;
import org.example.entities.Movie;
import org.example.repository.director.DirectorRepository;
import org.example.repository.movie.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevDataLoader {

    @Bean
    CommandLineRunner loadTestData(DirectorRepository directorRepository,
                                   MovieRepository movieRepository) {
        return args -> {
            System.out.println("Loading test data...");

            Director nolan = new Director();
            nolan.setFirstName("Christopher");
            nolan.setLastName("Nolan");
            directorRepository.save(nolan);

            Director spielberg = new Director();
            spielberg.setFirstName("Steven");
            spielberg.setLastName("Spielberg");
            directorRepository.save(spielberg);

            Movie inception = new Movie();
            inception.setTitle("Inception");
            inception.setDuration(148L);
            inception.setGenre("Sci-Fi");
            inception.setDirector(nolan);
            movieRepository.save(inception);

            Movie darkKnight = new Movie();
            darkKnight.setTitle("The Dark Knight");
            darkKnight.setDuration(152L);
            darkKnight.setGenre("Action");
            darkKnight.setDirector(nolan);
            movieRepository.save(darkKnight);

            Movie jaws = new Movie();
            jaws.setTitle("Jaws");
            jaws.setDuration(124L);
            jaws.setGenre("Thriller");
            jaws.setDirector(spielberg);
            movieRepository.save(jaws);

            System.out.println("Test data loaded successfully");
        };
    }
}
