package org.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectUnauthenticatedPost() throws Exception {
        String json = """
                {
                    "name": "UnauthorizedProduct",
                    "sku": "UN-001",
                    "quantity": 5
                }
                """;
        mockMvc.perform(post("/products")
                .with(csrf())
                .contentType("application/json")
                .content(json))
                .andExpect(status().isFound());
    }
}
