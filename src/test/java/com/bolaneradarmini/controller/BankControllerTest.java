package com.bolaneradarmini.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllBanks_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/banks"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void createBank_ShouldRequireAuthentication() throws Exception {
        String newBankJson = """
        { "name": "TestBank" }
        """;

        mockMvc.perform(post("/api/banks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBankJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createBank_WithAuth_ShouldAddBank() throws Exception {
        String newBankJson = """
        { "name": "AuthBank" }
        """;

        mockMvc.perform(post("/api/banks")
                        .with(httpBasic("admin", "innebandy"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBankJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("AuthBank"));
    }
}
