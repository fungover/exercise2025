package org.example;

import org.example.config.SecurityConfig;
import org.example.entities.Cat;
import org.example.filters.ApiKeyFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatController.class)
@Import({SecurityConfig.class, ApiKeyFilter.class})
class CatControllerTest {

    @MockitoBean
    CatRepository repository;

    @Autowired
    MockMvc mockMvc;

    // ========== GET /api/cats Tests ==========

    @Test
    void getCats_withApiKey_shouldReturnOk() throws Exception {
        List<Cat> cats = List.of(new Cat("Misse", 10, List.of()));
        Mockito.when(repository.findCatsBy()).thenReturn(cats);

        mockMvc.perform(get("/api/cats")
                        .header("X-API-KEY", "secret"))
                .andExpect(status().isOk());
    }

    @Test
    void getCats_withoutApiKey_shouldBeDenied() throws Exception {
        mockMvc.perform(get("/api/cats"))
                .andExpect(status().isForbidden());
    }

    // ========== POST /api/cats Tests ==========

    @Test
    void postCat_withApiKey_shouldReturnCreated() throws Exception {
        Cat cat = new Cat("Whiskers", 3, List.of());
        Mockito.when(repository.save(Mockito.any(Cat.class))).thenReturn(cat);

        mockMvc.perform(post("/api/cats")
                        .header("X-API-KEY", "secret")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Whiskers\", \"age\": 3, \"foodList\": []}"))
                .andExpect(status().isCreated());
    }

    @Test
    void postCat_withoutApiKey_shouldBeDenied() throws Exception {
        mockMvc.perform(post("/api/cats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Whiskers\", \"age\": 3}"))
                .andExpect(status().isForbidden());
    }
}