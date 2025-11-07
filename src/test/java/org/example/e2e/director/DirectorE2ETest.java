package org.example.e2e.director;

import org.example.dto.request.director.CreateDirectorRequest;
import org.example.dto.request.director.UpdateDirectorRequest;
import org.example.dto.response.director.DirectorResponse;
import org.example.entities.Director;
import org.example.entities.User;
import org.example.repository.director.DirectorRepository;
import org.example.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class DirectorE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

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
        directorRepository.deleteAll();
        userRepository.deleteAll();
    }

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
    }


    @ParameterizedTest
    @CsvSource({
            "123, CREATED",
            "456, FORBIDDEN"
    })
    void addDirectorShouldReturnCreated(String apiKey, HttpStatus expectedStatus) {
        CreateDirectorRequest cdr = new CreateDirectorRequest();
        cdr.setFirstName("James");
        cdr.setLastName("Gunn");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateDirectorRequest> request = new HttpEntity<>(cdr, headers);

        ResponseEntity<DirectorResponse> response = restTemplate.postForEntity("/api/director", request, DirectorResponse.class);

        assertEquals(expectedStatus, response.getStatusCode());

        if (expectedStatus == HttpStatus.CREATED) {
            assertNotNull(response.getBody());
            Director saved = directorRepository.findById(response.getBody().getId()).get();
            assertEquals("James", saved.getFirstName());
            assertEquals("Gunn", saved.getLastName());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "123, OK",
            "456, FORBIDDEN"
    })
    void updateDirectorShouldReturnOk(String apiKey, HttpStatus expectedStatus) {
        Director originalDirector = new Director("Original", "Director", List.of());
        Long directorId = directorRepository.save(originalDirector).getId();

        UpdateDirectorRequest udr = new UpdateDirectorRequest();
        udr.setFirstName("Updated");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateDirectorRequest> request = new HttpEntity<>(udr, headers);

        ResponseEntity<DirectorResponse> response = restTemplate.exchange("/api/director/{id}", HttpMethod.PUT, request, DirectorResponse.class, directorId);

        assertEquals(expectedStatus, response.getStatusCode());

        if (expectedStatus == HttpStatus.OK) {
            Director updatedDirector = directorRepository.findById(directorId).get();
            assertEquals("Updated", updatedDirector.getFirstName());
            assertEquals("Director", updatedDirector.getLastName());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "123, NO_CONTENT",
            "456, FORBIDDEN"
    })
    void deleteDirectorShouldReturnNoContent(String apiKey, HttpStatus expectedStatus) {
        Director originalDirector = new Director("Original", "Director", List.of());
        Long directorId = directorRepository.save(originalDirector).getId();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange("/api/director/{id}", HttpMethod.DELETE, request, Void.class, directorId);

        assertEquals(expectedStatus, response.getStatusCode());

        if (expectedStatus == HttpStatus.NO_CONTENT) {
            assertEquals(0, directorRepository.count());

            Director deletedDirector = directorRepository.findById(directorId).orElse(null);
            assertNull(deletedDirector);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "456, OK",
            "789, UNAUTHORIZED"
    })
    void getDirectorShouldReturnDirector(String apiKey, HttpStatus expectedStatus) {
        Director directorOne = new Director("Director", "One", List.of());
        directorRepository.save(directorOne);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request = new HttpEntity<>(headers);

        if (apiKey.equals("456")) {
            ResponseEntity<DirectorResponse> response = restTemplate.exchange("/api/director/{id}", HttpMethod.GET, request, DirectorResponse.class, directorOne.getId());

            assertEquals(expectedStatus, response.getStatusCode());

            if (expectedStatus == HttpStatus.OK) {
                assertNotNull(response.getBody());
                assertEquals(directorOne.getId(), response.getBody().getId());
                assertEquals(directorOne.getFirstName(), response.getBody().getFirstName());
                assertEquals(directorOne.getLastName(), response.getBody().getLastName());
            }
        } else {
            ResponseEntity<String> response = restTemplate.exchange("/api/director/{id}", HttpMethod.GET, request, String.class, directorOne.getId());

            assertEquals(expectedStatus, response.getStatusCode());

            if (expectedStatus == HttpStatus.UNAUTHORIZED) {
                assertEquals("Unauthorized", response.getBody());
            }
        }
    }

}
