package org.example.controller;

import org.example.security.SecurityConfig;
import org.example.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AnimalController.class)
// the test might  a bit slower, and it hits the DB, but access all the real stuff.
// so it will add a lion everytime to our DB.
//@SpringBootTest
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class AnimalControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    AnimalService animalService;

    @Test
    void unauthenticatedCannotPost() throws Exception {
        mvc.perform(post("/api/animals").contentType(MediaType.APPLICATION_JSON)
                                        .content(
                                          "{\"name\":\"Leo\",\"species\":\"Lion\"}"))
           .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminCanPost() throws Exception {
        mvc.perform(post("/api/animals").contentType(MediaType.APPLICATION_JSON)
                                        .content(
                                          "{\"name\":\"Leo\",\"species\":\"Lion\"}")
                                        .with(csrf()))

           .andExpect(status().isCreated());
    }

    @Test
    void getListIsPublic() throws Exception {
        mvc.perform(get("/api/animals"))
           .andExpect(status().isOk());
    }

}