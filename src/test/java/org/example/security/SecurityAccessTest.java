package org.example.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void adminShouldAccessWorkoutPage() throws Exception {
        mockMvc.perform(get("/api/workouts")
                        .with(httpBasic("admin", "adminpass")))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllWorkouts_unauthenticated_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/workouts"))
                .andExpect(status().isUnauthorized());
    }

}

