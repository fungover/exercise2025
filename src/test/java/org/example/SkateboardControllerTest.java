package org.example;

import org.example.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@Sql("test-data.sql")
public class SkateboardControllerTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.44")
            .withDatabaseName("decks")
            .withUsername("user")
            .withPassword("password");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Test
    void whenNoJwt_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/decks"))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenValidJwt_thenOk_apiDecks() throws Exception {
        String token = "Bearer " + jwtService.generateToken("user");

        mockMvc.perform(get("/api/decks")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidJwt_thenOk_apiDecksId() throws Exception {
        String token = "Bearer " + jwtService.generateToken("user");

        mockMvc.perform(get("/api/decks/id/1")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidJwt_thenOk_apiDecksBrand() throws Exception {
        String token = "Bearer " + jwtService.generateToken("user");

        mockMvc.perform(get("/api/decks/brand/anyBrand")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    void whenValidJwt_thenOk_apiTrucksBoardWidth() throws Exception {
        String token = "Bearer " + jwtService.generateToken("user");

        mockMvc.perform(get("/api/trucks/8.0")
                        .header("Authorization", token))
                .andExpect(status().isOk());
    }
}
