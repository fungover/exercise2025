package org.example.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.SecurityConfig;
import org.example.domain.ExerciseEntity;
import org.example.repo.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExerciseController.class)       // Starts a small slice with just the ExerciseController
@Import(SecurityConfig.class)                             // loads our security config too
@AutoConfigureMockMvc                                    // gives us a MockMvc to use with security filter chain
class ExerciseControllerTest {

    @Autowired
    MockMvc mvc;                               // HTTP-klient in memory
    @Autowired
    ObjectMapper om;                           // For reading JSON responses

    @MockitoBean
    ExerciseRepository repo;                 // mocks the ExerciseRepository

    @Test
    void shouldReturnExercisesWithoutAuthentication() throws Exception {
        // fake data from repo
        var e = new ExerciseEntity();
        e.setName("Bench Press");

        org.mockito.Mockito.when(repo.findAll()).thenReturn(List.of(e)); // when repo.findAll() is called, return this list.

        // GET against our public endpoint /api/exercises
        var mvcResult = mvc.perform(get("/api/exercises"))
                .andExpect(status().isOk())              // Expect 200 OK
                .andReturn();


        var json = mvcResult.getResponse().getContentAsString(); // response body as string
        var list = om.readValue(json, new TypeReference<List<Map<String, Object>>>() {
        });
        assertThat(list).hasSize(1); // one item in the list
        assertThat(list.get(0).get("name")).isEqualTo("Bench Press"); // name matches
    }
}
