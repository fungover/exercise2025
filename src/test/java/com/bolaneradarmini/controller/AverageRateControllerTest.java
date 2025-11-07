package com.bolaneradarmini.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AverageRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Hjälpmetod för att se till att banker finns innan testerna körs
    private void ensureExampleBanksExist() throws Exception {
        // Ta bort eventuella gamla banker först
        mockMvc.perform(delete("/api/banks")
                        .with(httpBasic("admin", "innebandy")))
                .andExpect(status().isOk());

        // Ladda sedan in exempelbankerna
        mockMvc.perform(post("/api/banks/load-example-banks")
                        .with(httpBasic("admin", "innebandy")))
                .andExpect(status().isOk());
    }

    // Körs före varje test – rensar rates och ser till att banker finns
    @BeforeEach
    void setupDatabase() throws Exception {
        mockMvc.perform(delete("/api/rates")
                        .with(httpBasic("admin", "innebandy")))
                .andExpect(status().isOk());

        ensureExampleBanksExist();
    }

    @Test
    void getAllRates_ShouldReturnEmptyListInitially() throws Exception {
        mockMvc.perform(get("/api/rates"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void loadExampleData_ShouldRequireAuthentication() throws Exception {
        mockMvc.perform(post("/api/rates/loadExampleData"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void loadExampleData_WithAuth_ThenGet_ShouldReturnData() throws Exception {
        // Ladda in testdata med Basic auth
        mockMvc.perform(post("/api/rates/load-example-data")
                        .with(httpBasic("admin", "innebandy")))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Example data")));

        // Hämta alla räntor igen
        mockMvc.perform(get("/api/rates"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", greaterThan(0)));
    }

    @Test
    void createNewRate_ShouldAddRateToDatabase() throws Exception {
        // Hämta antalet poster innan
        String before = mockMvc.perform(get("/api/rates"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int countBefore = new org.json.JSONArray(before).length();

        // Skapa ny rate kopplad till en befintlig bank
        String newRateJson = """
        {
            "bank": {"name": "Swedbank"},
            "rate": 2.55,
            "date": "2025-11-05"
        }
        """;

        var result = mockMvc.perform(post("/api/rates")
                        .with(httpBasic("admin", "innebandy"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newRateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(2.55))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertTrue(response.contains("2.55")); // enkel extra koll att svaret innehåller rätt rate

        // Verifiera att antalet poster har ökat
        String after = mockMvc.perform(get("/api/rates"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int countAfter = new org.json.JSONArray(after).length();
        assertTrue(countAfter >= countBefore);
    }

    @Test
    void deleteAllRates_ShouldDeleteAllRates() throws Exception {
        // Ladda in exempeldata
        mockMvc.perform(post("/api/rates/load-example-data")
                        .with(httpBasic("admin", "innebandy")))
                .andExpect(status().isOk());

        // Hämta antalet poster innan
        String before = mockMvc.perform(get("/api/rates"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int countBefore = new org.json.JSONArray(before).length();
        assertTrue(countBefore > 0);

        // Ta bort alla poster
        mockMvc.perform(delete("/api/rates")
                        .with(httpBasic("admin", "innebandy")))
                .andExpect(status().isOk());

        // Kolla att antalet är 0
        String after = mockMvc.perform(get("/api/rates"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int countAfter = new org.json.JSONArray(after).length();
        assertEquals(0, countAfter);
    }
}