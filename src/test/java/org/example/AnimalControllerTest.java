package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.SecurityConfig;
import org.example.entity.Pet;
import org.example.service.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(AnimalController.class)
@Import(SecurityConfig.class)
public class AnimalControllerTest {

    private final LocalDateTime TEST_DATE = LocalDateTime.of(2025, 12, 12, 12, 0);


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AnimalService animalService;

    private Pet testPet;

    @BeforeEach
    void setUp() {
        testPet = new Pet();
        testPet.setCreatedAt(TEST_DATE);
        testPet.setId(1);
        testPet.setName("Buddy");
        testPet.setSpecies("Dog");
        testPet.setAge(3);
        testPet.setBirthDate("2021-01-01");
        testPet.setCreatedAt(TEST_DATE);
    }

    @Test
    void testGetPet_Any200() throws Exception {
        when(animalService.findAllAnimals()).thenReturn(List.of(testPet));

        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Buddy"))
                .andExpect(jsonPath("$[0].age").value(3))
                .andExpect(jsonPath("$[0].species").value("Dog"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateAnimal_Admin201() throws Exception {
        when(animalService.createAnimal(any(Pet.class))).thenReturn(testPet);

        ResultActions result = mockMvc.perform(post("/api/animals")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testPet)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/animals/1"))
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.age").value(3))
                .andExpect(jsonPath("$.species").value("Dog"));
        System.out.println(result.andReturn().getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "user")
    void testCreateAnimal_User403() throws Exception {
        when(animalService.createAnimal(any(Pet.class))).thenReturn(testPet);

        mockMvc.perform(post("/api/animals")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testPet)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteAnimal_Admin204() throws Exception {
        when(animalService.deleteAnimal(1)).thenReturn(true);

        mockMvc.perform(delete("/api/animals/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user")
    void testDeleteAnimal_User403() throws Exception {
        when(animalService.deleteAnimal(1)).thenReturn(true);

        mockMvc.perform(delete("/api/animals/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateAnimal_Admin200() throws Exception {

        Pet requestPet = new Pet();
        requestPet.setName("Max");
        requestPet.setAge(10);
        requestPet.setSpecies("Cat");

        Pet updatedPet = new Pet();
        updatedPet.setId(1);
        updatedPet.setName("Max");
        updatedPet.setAge(10);
        updatedPet.setSpecies("Cat");

        when(animalService.updateAnimal(any(Pet.class))).thenReturn(updatedPet);

        mockMvc.perform(put("/api/animals/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestPet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Max"))
                .andExpect(jsonPath("$.age").value(10))
                .andExpect(jsonPath("$.species").value("Cat"));
    }

    @Test
    @WithMockUser(username = "user")
    void testUpdateAnimal_User403() throws Exception {

        Pet requestPet = new Pet();
        requestPet.setName("Max");
        requestPet.setAge(10);
        requestPet.setSpecies("Cat");

        Pet updatedPet = new Pet();
        updatedPet.setId(1);
        updatedPet.setName("Max");
        updatedPet.setAge(10);
        updatedPet.setSpecies("Cat");

        when(animalService.updateAnimal(any(Pet.class))).thenReturn(updatedPet);

        mockMvc.perform(put("/api/animals/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestPet)))
                .andExpect(status().isForbidden());
    }



}
