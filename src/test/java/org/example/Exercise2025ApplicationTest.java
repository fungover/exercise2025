package org.example;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Sql("cats.sql")
//@ActiveProfiles("dev")
@Transactional
class Exercise2025ApplicationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.44");

    @Autowired
    private TestRestTemplate template;

    @Test
    void contextLoads() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "secret");
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Cat[]> response =
                this.template
                        .exchange("/api/cats",
                                HttpMethod.GET,
                                request, Cat[].class);
        assertThat(response.getStatusCode().is2xxSuccessful());
        assertThat(response.getBody()).extracting("name")
                .contains("Misse");
    }
}
