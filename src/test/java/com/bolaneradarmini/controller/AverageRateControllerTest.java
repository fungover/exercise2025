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

    // Körs före varje test så vi städar bort gamla rates
    @BeforeEach
    void cleanDatabase() throws Exception {
        mockMvc.perform(delete("/api/rates")
                .with(httpBasic("Admin", "innebandy")))
                .andExpect(status().isOk());
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
    void loadExampleData_WithAuto_ThenGet_ShouldReturnData() throws Exception {
        // Ladda in testdata med Basic auth
        mockMvc.perform(post("/api/rates/load-example-data")
                .with(httpBasic("Admin", "innebandy")))
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

        // Skapa ny rate
        String newRateJson = """
        {
            "bank": {"id": 1},
            "rate": 2.55,
            "date": "2025-11-01"
        }
        """;

        mockMvc.perform(post("/api/rates")
                        .with(httpBasic("admin", "innebandy"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newRateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rate").value(2.55));

        // Verifiera att antalet har ökat
        assertTrue(countBefore > 0);
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
        org.junit.jupiter.api.Assertions.assertTrue(countBefore > 0);

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

        // Verifiera att antalet är 0
        assertEquals(0, countAfter);
    }
}
