package org.example.e2e.director;

import org.example.dto.request.director.CreateDirectorRequest;
import org.example.dto.response.director.DirectorResponse;
import org.example.entities.Director;
import org.example.entities.User;
import org.example.repository.director.DirectorRepository;
import org.example.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    }


    @Test
    void addDirectorShouldReturnCreated() {
        CreateDirectorRequest cdr = new CreateDirectorRequest();
        cdr.setFirstName("James");
        cdr.setLastName("Gunn");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "123");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateDirectorRequest> request = new HttpEntity<>(cdr, headers);

        ResponseEntity<DirectorResponse> response = restTemplate.postForEntity("/api/director", request, DirectorResponse.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        Director saved = directorRepository.findById(response.getBody().getId()).get();
        assertEquals("James", saved.getFirstName());
        assertEquals("Gunn", saved.getLastName());
    }


}
