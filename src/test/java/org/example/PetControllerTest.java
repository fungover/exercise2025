package org.example;

import org.example.enteties.Pet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class PetControllerTest {
    @MockitoBean
    PetRepository petRepository;

    @MockitoBean
    PetService petService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    void getAllPetsShouldReturnPetsWhenAuthorized() throws Exception {
        List<Pet> pets = List.of(new Pet("Bergman", "Dog", 40, 70));
        Mockito.when(petRepository.findAll()).thenReturn(pets);

        mockMvc.perform(get("/api/pets").header("X-API-KEY", "secret"))
                .andExpect(status().isOk());
    }
    @Test
    void getAllPetsShouldReturn401WhenUnauthorized() throws Exception{
        List<Pet> pets = List.of(new Pet("Bergman", "Dog", 40, 70));
        Mockito.when(petRepository.findAll()).thenReturn(pets);

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    @WithMockUser
    void getPetShouldReturnPetWhenAuthorized() throws Exception {
        Pet pet = new Pet("Bergman", "Dog", 40, 70);
        Mockito.when(petRepository.findPetById(1)).thenReturn(Optional.of(pet));

        mockMvc.perform(get("/api/pets/1").header("X-API-KEY", "secret"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username="api", roles = {"API"})
    void createPetShouldReturnPet() throws Exception {
        Pet pet = new Pet("Bergman", "Dog", 50, 50);
        Mockito.when(petRepository.save(pet)).thenReturn(pet);

        mockMvc.perform(post("/api/pets").with(csrf()).header("X-API-KEY", "secret").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Bergman\", \"species\": \"dog\",\"hunger\": 50,\"happiness\": 50}"))
                .andExpect(status().isCreated());
    }
    @Test
    @WithMockUser(username="admin", roles = {"ADMIN"})
    void deletePetShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/pets/1").with(csrf()).header("X-API-KEY", "secret"))
                .andExpect(status().isNoContent());
    }
    @Test
    @WithMockUser(username="admin", roles={"ADMIN"})
    void feedPetShouldReturnOk() throws Exception {
        Pet pet = new Pet("Bergman", "Dog", 40, 60, 2);
        Mockito.when(petRepository.findPetById(2)).thenReturn(Optional.of(pet));
        PetDTO petDTO = new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), pet.getHappiness(), pet.getId());
        PetDTO petDTO2 = new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger()-10, pet.getHappiness(), pet.getId());
        Mockito.when(petService.feedPet(petDTO)).thenReturn(petDTO2);
        mockMvc.perform(put("/api/pets/2/feed").with(csrf()).header("X-API-KEY", "secret"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    void playWithPetShouldReturnOk() throws Exception {
        Pet pet = new Pet("Bergman", "Dog", 40, 40, 1);
        Mockito.when(petRepository.findPetById(1)).thenReturn(Optional.of(pet));
        PetDTO petDTO = new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), pet.getHappiness(), pet.getId());
        PetDTO petDTO2 = new PetDTO(pet.getName(), pet.getSpecies(), pet.getHunger(), pet.getHappiness()+10, pet.getId());
        Mockito.when(petService.playWithPet(petDTO)).thenReturn(petDTO2);
        mockMvc.perform(put("/api/pets/1/play").with(csrf()).header("X-API-KEY", "secret"))
                .andExpect(status().isOk());
    }

}