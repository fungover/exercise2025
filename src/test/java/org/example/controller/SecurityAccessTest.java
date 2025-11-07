package org.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityAccessTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Testar att GET alltid är tillåten (även utan inloggning)
     */
    @Test
    void getAllRates_ShouldBeAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/rates"))
                .andExpect(status().isOk());
    }

    /**
     * Testar att POST utan inloggning ska nekas (401 Unauthorized)
     */
    @Test
    void postWithoutAuth_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(post("/api/rates/load-example-data"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Testar att POST med giltig inloggning fungerar
     */
    @Test
    void postWithAuth_ShouldReturnOk() throws Exception {
        mockMvc.perform(post("/api/rates/load-example-data")
                        .with(httpBasic("admin", "innebandy")))
                .andExpect(status().isOk());
    }

    /**
     * Testar att DELETE utan auth blockeras
     */
    @Test
    void deleteWithoutAuth_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/rates"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Testar att DELETE med auth är tillåtet
     */
    @Test
    void deleteWithAuth_ShouldReturnOk() throws Exception {
        mockMvc.perform(delete("/api/rates")
                        .with(httpBasic("admin", "innebandy")))
                .andExpect(status().isOk());
    }
}