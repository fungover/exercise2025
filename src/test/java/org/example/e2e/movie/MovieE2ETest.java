package org.example.e2e.movie;

import org.example.dto.request.director.CreateDirectorRequest;
import org.example.dto.request.director.UpdateDirectorRequest;
import org.example.dto.request.movie.CreateMovieRequest;
import org.example.dto.request.movie.UpdateMovieRequest;
import org.example.dto.response.director.DirectorResponse;
import org.example.dto.response.movie.MovieResponse;
import org.example.entities.Director;
import org.example.entities.Movie;
import org.example.entities.User;
import org.example.repository.director.DirectorRepository;
import org.example.repository.movie.MovieRepository;
import org.example.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class MovieE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.44")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeEach
    void cleanDatabase() {
        movieRepository.deleteAll();
        userRepository.deleteAll();
    }

    private Director mainDirector;

    @BeforeEach
    void setUp() {
        User adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities("ROLE_ADMIN")
                .build();
        adminUser.setApiKey("123");
        userRepository.save(adminUser);

        User userUser = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user"))
                .authorities("ROLE_USER")
                .build();
        userUser.setApiKey("456");
        userRepository.save(userUser);

        Director director = new Director("Test", "Director", List.of());
        this.mainDirector = directorRepository.save(director);
    }


    @ParameterizedTest
    @CsvSource({
            "123, CREATED",
            "456, FORBIDDEN"
    })
    void addMovieShouldReturnCreated(String apiKey, HttpStatus expectedStatus) {
        CreateMovieRequest cmr = new CreateMovieRequest();
        cmr.setTitle("Test");
        cmr.setDuration(120L);
        cmr.setGenre("Sci-Fi");
        cmr.setDirectorId(mainDirector.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateMovieRequest> request = new HttpEntity<>(cmr, headers);

        ResponseEntity<MovieResponse> response = restTemplate.postForEntity("/api/movie", request, MovieResponse.class);

        assertEquals(expectedStatus, response.getStatusCode());

        if (expectedStatus == HttpStatus.CREATED) {
            assertNotNull(response.getBody());
            Movie saved = movieRepository.findById(response.getBody().getId()).get();
            assertEquals("Test", saved.getTitle());
            assertEquals(120L, saved.getDuration());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "123, OK",
            "456, FORBIDDEN"
    })
    void updateDirectorShouldReturnOk(String apiKey, HttpStatus expectedStatus) {
        Movie originalMovie = new Movie("Test", 120L, "Sci-Fi", mainDirector);
        Long movieId = movieRepository.save(originalMovie).getId();

        UpdateMovieRequest umr = new UpdateMovieRequest();
        umr.setTitle("Updated");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateMovieRequest> request = new HttpEntity<>(umr, headers);

        ResponseEntity<MovieResponse> response = restTemplate.exchange("/api/movie/{id}", HttpMethod.PUT, request, MovieResponse.class, movieId);

        assertEquals(expectedStatus, response.getStatusCode());

        if (expectedStatus == HttpStatus.OK) {
            Movie updatedMovie = movieRepository.findById(movieId).get();
            assertEquals("Updated", updatedMovie.getTitle());
            assertEquals(120L, updatedMovie.getDuration());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "123, NO_CONTENT",
            "456, FORBIDDEN"
    })
    void deleteMovieShouldReturnNoContent(String apiKey, HttpStatus expectedStatus) {
        Movie originalMovie = new Movie("Original", 120L, "Sci-Fi", mainDirector);
        Long movieId = movieRepository.save(originalMovie).getId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange("/api/movie/{id}", HttpMethod.DELETE, request, Void.class, movieId);

        assertEquals(expectedStatus, response.getStatusCode());

        if (expectedStatus == HttpStatus.NO_CONTENT) {
            assertEquals(0, movieRepository.count());

            Movie deletedMovie = movieRepository.findById(movieId).orElse(null);
            assertNull(deletedMovie);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "456, OK",
            "789, UNAUTHORIZED"
    })
    void getMovieShouldReturnMovie(String apiKey, HttpStatus expectedStatus) {
        Movie movieOne = new Movie("Test Movie", 120L, "Sci-Fi", mainDirector);
        movieRepository.save(movieOne);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        if (apiKey.equals("456")) {
            ResponseEntity<MovieResponse> response = restTemplate.exchange(
                    "/api/movie/{id}",
                    HttpMethod.GET,
                    request,
                    MovieResponse.class,
                    movieOne.getId()
            );

            assertEquals(expectedStatus, response.getStatusCode());

            if (expectedStatus == HttpStatus.OK) {
                assertNotNull(response.getBody());
                assertEquals(movieOne.getId(), response.getBody().getId());
                assertEquals(movieOne.getTitle(), response.getBody().getTitle());
                assertEquals(movieOne.getDuration(), response.getBody().getDuration());
                assertEquals(movieOne.getGenre(), response.getBody().getGenre());
            }
        } else {
            ResponseEntity<String> response = restTemplate.exchange(
                    "/api/movie/{id}",
                    HttpMethod.GET,
                    request,
                    String.class,
                    movieOne.getId()
            );

            assertEquals(expectedStatus, response.getStatusCode());

            if (expectedStatus == HttpStatus.UNAUTHORIZED) {
                assertEquals("Unauthorized", response.getBody());
            }
        }
    }

}
