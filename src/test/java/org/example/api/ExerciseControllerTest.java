package org.example.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.SecurityConfig;
import org.example.domain.ExerciseEntity;
import org.example.repo.ExerciseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExerciseController.class)
@Import(SecurityConfig.class)
class ExerciseControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper om;

    @MockitoBean
    ExerciseRepository repo;

    @Test
    void getExercises_returnsList_forAnonymousUser_accordingToSecurityConfig() throws Exception {
        var e = new ExerciseEntity();
        e.setName("Bench Press");
        e.setMuscleGroup("Chest");
        when(repo.findAll()).thenReturn(List.of(e));

        var res = mvc.perform(get("/api/exercises"))
                .andExpect(status().isOk())
                .andReturn();

        var json = res.getResponse().getContentAsString();
        var list = om.readValue(json, new TypeReference<List<Map<String, Object>>>() {
        });
        assertThat(list).hasSize(1);

        var first = list.get(0);
        assertThat(first.get("name")).isEqualTo("Bench Press");
        assertThat(first.get("muscleGroup")).isEqualTo("Chest");
    }

}
