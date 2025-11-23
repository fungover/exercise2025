package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.RecipeItemUpsertDto;
import org.example.dto.RecipeUpsertDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
class RecipeControllerIntegrationTest {

    // One MySQL container shared for all tests in this class
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.36")
            .withDatabaseName("spring_test")
            .withUsername("user")
            .withPassword("secret");

    // Plug container values into Spring Boots datasource properties
    @DynamicPropertySource
    static void configureDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void listRecipes_returnsSeedData_forAuthenticatedUser() throws Exception {
        // DevDataLoader created "Tacos" and "Pasta" into the container database
        mockMvc.perform(
                        get("/api/recipes")
                                .with(httpBasic("user", "user"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title", hasItems("Tacos", "Pasta")));

    }

    @Test
    void listRecipes_returns401_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/recipes"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createRecipe_returns403_forUserRole() throws Exception {
        RecipeUpsertDto dto = new RecipeUpsertDto(
                "Forbidden Test",
                "Trying to POST as USER",
                List.of(new RecipeItemUpsertDto("Salt", 1.0, "tsp"))
        );
        String body = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                        post("/api/recipes")
                                .with(httpBasic("user", "user"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void createRecipe_persistsToDatabase_forAdmin() throws Exception {
        RecipeUpsertDto dto = new RecipeUpsertDto(
                "Integration Test Recipe",
                "Created in integration test",
                List.of(new RecipeItemUpsertDto("Flour", 1.0, "tsp"))
        );
        String body = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                        post("/api/recipes")
                                .with(httpBasic("admin", "admin"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isCreated());

        // To verify it's there
        mockMvc.perform(
                        get("/api/recipes")
                                .with(httpBasic("user", "user"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.title == 'Integration Test Recipe')]").exists());
    }
}
