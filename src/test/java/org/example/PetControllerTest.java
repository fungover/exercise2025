package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String API_KEY = "secret";

    @Test
    void getPetsUnauthorizedWithoutApiKey() throws Exception {
        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getPetsAuthorizedWithApiKey() throws Exception {
        mockMvc.perform(get("/api/pets")
                .header("X-Api-Key", API_KEY))
                .andExpect(status().isOk());
    }

    @Test
    void addPetAuthorized() throws Exception {
        String json = """
            {
              "name": "Liam",
              "age": 2,
              "species": "Katt",
              "hungerLevel": 0,
              "happiness": 50
            }
            """;

        mockMvc.perform(post("/api/addPet")
                .header("X-Api-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Liam")));
    }

    @Test
    void getPetByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/pets/999")
                .header("X-Api-Key", API_KEY))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePetNotFound() throws Exception {
        mockMvc.perform(delete("/api/deletePet/999")
                .header("X-Api-Key", API_KEY))
                .andExpect(status().isNotFound());
    }

    @Test
    void feedPetUnauthorizedWithoutApiKey() throws Exception {
        mockMvc.perform(post("/api/feed/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void playPetAuthorized() throws Exception {
        String json = """
        {
          "name": "Erik",
          "age": 15,
          "species": "Hund",
          "hungerLevel": 50,
          "happiness": 70
        }
        """;

        MvcResult result = mockMvc.perform(post("/api/addPet")
                .header("X-Api-Key", API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        int createdId = mapper.readTree(responseBody).get("id").asInt();

        mockMvc.perform(post("/api/play/" + createdId)
                .header("X-Api-Key", API_KEY))
                .andExpect(status().isOk());
    }
}
