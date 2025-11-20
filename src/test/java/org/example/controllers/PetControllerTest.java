package org.example.controllers;

import org.example.config.SecurityConfig;
import org.example.entities.Pet;
import org.example.services.PetService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(PetController.class)
@Import(SecurityConfig.class)
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetService petService;

    // Test data
    private Pet createTestPet(Long id, String name, String species, int hungerLevel, int happinessLevel) {
        return new Pet(id, name, species, hungerLevel, happinessLevel);
    }

    // GET /api/pets & GET /api/pets/{id} (Public endpoint)

    @Test
    @WithAnonymousUser
    void getAllPetsShouldReturnListOfPetsForAnonymousUser() throws Exception {
        List<Pet> pets = Arrays.asList(
                createTestPet(1L, "Rex", "Dog", 5, 8),
                createTestPet(2L, "Garfield", "Cat", 10, 2)
        );
        when(petService.getAllPets(null, null, null, null, null)).thenReturn(pets);

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Rex")))
                .andExpect(jsonPath("$[0].species", is("Dog")))
                .andExpect(jsonPath("$[1].name", is("Garfield")));

        verify(petService, times(1)).getAllPets(null, null, null, null, null);

    }

    @Test
    @WithAnonymousUser
    void getPetByIdShouldReturnPetForAnonymousUser() throws Exception {
        Pet pet = createTestPet(1L, "Rex", "Dog", 5, 8);
        when(petService.getPetById(1L)).thenReturn(pet);

        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Rex")))
                .andExpect(jsonPath("$.species", is("Dog")))
                .andExpect(jsonPath("$.hungerLevel", is(5)))
                .andExpect(jsonPath("$.happinessLevel", is(8)));

        verify(petService, times(1)).getPetById(1L);
    }

    // Feed pets for different roles (Anonymous, User, Admin):
    @Test
    @WithAnonymousUser
    void feedPetShouldReturn401ForUnauthorizedUser() throws Exception {

        mockMvc.perform(put("/api/pets/1/feed")
                .with(csrf()))
                .andExpect(status().isUnauthorized());

        verify(petService, never()).feedPet(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    void feedPetShouldReturn200ForAuthorizedUser() throws Exception {
        Pet pet = createTestPet(1L, "Rex", "Dog", 5, 8);
        when(petService.feedPet(1L)).thenReturn(pet);

        mockMvc.perform(put("/api/pets/1/feed")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Rex")))
                .andExpect(jsonPath("$.species", is("Dog")))
                .andExpect(jsonPath("$.hungerLevel", is(5)))
                .andExpect(jsonPath("$.happinessLevel", is(8)));
        verify(petService, times(1)).feedPet(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void feedPetShouldReturn200ForAdmin() throws Exception {
        Pet pet = createTestPet(1L, "Garfield", "Cat", 5, 8);
        when(petService.feedPet(1L)).thenReturn(pet);

        mockMvc.perform(put("/api/pets/1/feed")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Garfield")))
                .andExpect(jsonPath("$.species", is("Cat")))
                .andExpect(jsonPath("$.hungerLevel", is(5)))
                .andExpect(jsonPath("$.happinessLevel", is(8)));
        verify(petService, times(1)).feedPet(1L);
    }

    // Play with pets for different roles (Anonymous, User, Admin):
    @Test
    @WithAnonymousUser
    void playWithPetShouldReturn401ForUnauthorizedUser() throws Exception {

        mockMvc.perform(put("/api/pets/1/play")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());

        verify(petService, never()).playWithPet(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    void playWithPetShouldReturn200ForAuthorizedUser() throws Exception {
        Pet pet = createTestPet(1L, "Bella", "Cat", 5, 8);
        when(petService.playWithPet(1L)).thenReturn(pet);

        mockMvc.perform(put("/api/pets/1/play")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Bella")))
                .andExpect(jsonPath("$.species", is("Cat")))
                .andExpect(jsonPath("$.hungerLevel", is(5)))
                .andExpect(jsonPath("$.happinessLevel", is(8)));
        verify(petService, times(1)).playWithPet(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void playWithPetShouldReturn200ForAdmin() throws Exception {
        Pet pet = createTestPet(1L, "Ben", "Bird", 5, 8);
        when(petService.playWithPet(1L)).thenReturn(pet);

        mockMvc.perform(put("/api/pets/1/play")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Ben")))
                .andExpect(jsonPath("$.species", is("Bird")))
                .andExpect(jsonPath("$.hungerLevel", is(5)))
                .andExpect(jsonPath("$.happinessLevel", is(8)));
        verify(petService, times(1)).playWithPet(1L);
    }

    // Only Admin can create pets
    @Test
    @WithAnonymousUser
    void createPetShouldReturn401ForUnauthorizedUser() throws Exception {
        String petJson = """
                {
                    "name": "Gustav",
                    "species": "Cat"
                }
                """;
        mockMvc.perform(post("/api/pets")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isUnauthorized());

        verify(petService, never()).addPet(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createPetShouldReturn403ForAuthorizedUser() throws Exception {
        String petJson = """
                {
                    "name": "Gustav",
                    "species": "Cat"
                }
                """;
        mockMvc.perform(post("/api/pets")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isForbidden());

        verify(petService, never()).addPet(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createPetShouldReturn201ForAdmin() throws Exception {
        Pet createdPet = createTestPet(7L, "Bella", "Dog", 5, 5);
        when(petService.addPet(any())).thenReturn(createdPet);

        String petJson = """
                {
                    "name": "Bella",
                    "species": "Dog"
                }
                """;

        mockMvc.perform(post("/api/pets")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(7)))
                .andExpect(jsonPath("$.name", is("Bella")))
                .andExpect(jsonPath("$.species", is("Dog")));

        verify(petService, times(1)).addPet(any());
    }

    // Only Admin can delete pets
    @Test
    @WithAnonymousUser
    void deletePetShouldReturn401ForUnauthorizedUser() throws Exception {
        mockMvc.perform(delete("/api/pets/1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());

        verify(petService, never()).deletePet(any());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deletePetShouldReturn403ForAuthorizedUser() throws Exception {

        mockMvc.perform(delete("/api/pets/1")
                        .with(csrf()))
                .andExpect(status().isForbidden());

        verify(petService, never()).deletePet(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deletePetShouldReturn204ForAdmin() throws Exception {
        Pet deletedPet = createTestPet(7L, "Spot", "Dog", 5, 5);
        when(petService.deletePet(7L)).thenReturn(deletedPet);

        mockMvc.perform(delete("/api/pets/7")
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(petService, times(1)).deletePet(7L);
    }

}
