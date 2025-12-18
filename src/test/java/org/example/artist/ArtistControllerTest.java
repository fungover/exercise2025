package org.example.artist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void unauthenticatedAccessFails() throws Exception {
        mockMvc.perform(get("/api/artists"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatedAccessSucceeds() throws Exception {
        mockMvc.perform(get("/api/artists")
                        .with(httpBasic("user", "pass")))
                .andExpect(status().isOk());
    }
}


