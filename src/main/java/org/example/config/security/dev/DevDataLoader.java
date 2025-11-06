package org.example.config.security.dev;

import org.example.entities.Director;
import org.example.entities.Movie;
import org.example.entities.User;
import org.example.repository.director.DirectorRepository;
import org.example.repository.movie.MovieRepository;
import org.example.repository.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevDataLoader {

    @Bean
    CommandLineRunner loadTestData(DirectorRepository directorRepository,
                                   MovieRepository movieRepository,
                                   UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println("Loading test data...");

            // Directors
            Director nolan = new Director();
            nolan.setFirstName("Christopher");
            nolan.setLastName("Nolan");
            directorRepository.save(nolan);

            Director spielberg = new Director();
            spielberg.setFirstName("Steven");
            spielberg.setLastName("Spielberg");
            directorRepository.save(spielberg);

            // Movies
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

            // Users
            User adminUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .authorities("ROLE_ADMIN")
                    .build();
            adminUser.setApiKey("123");
            userRepository.save(adminUser);

            User regularUser = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user"))
                    .authorities("ROLE_USER")
                    .build();
            regularUser.setApiKey("456");
            userRepository.save(regularUser);

            System.out.println("Test data loaded successfully");
        };
    }
}
