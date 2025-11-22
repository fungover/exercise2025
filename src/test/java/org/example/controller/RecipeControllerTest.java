package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.SecurityConfig;
import org.example.dto.RecipeItemUpsertDto;
import org.example.dto.RecipeListResponse;
import org.example.dto.RecipeResponse;
import org.example.dto.RecipeUpsertDto;
import org.example.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
class RecipeControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    RecipeService recipeService;

    @Test
    void unauthenticatedCannotListRecipes() throws Exception {
        mvc.perform(get("/api/recipes"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void userCanListRecipes_andWeReturnMockedData() throws Exception {
        List<RecipeListResponse> mocked = List.of(
                new RecipeListResponse(1, "Mock Tacos", "Mock instructions"),
                new RecipeListResponse(2, "Mock Pasta", "More mock instructions")
        );
        given(recipeService.listAll()).willReturn(mocked);

        mvc.perform(get("/api/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Mock Tacos"))
                .andExpect(jsonPath("$[1].title").value("Mock Pasta"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void userGets403WhenCreatingRecipe() throws Exception {
        RecipeUpsertDto dto = new RecipeUpsertDto(
                "Forbidden Test",
                "Trying to POST as USER",
                List.of(new RecipeItemUpsertDto("Salt", 1.0, "tsp"))
        );
        String body = objectMapper.writeValueAsString(dto);

        mvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminCanCreateRecipe() throws Exception {
        RecipeUpsertDto dto = new RecipeUpsertDto(
                "Mock Created",
                "Created via controller test",
                List.of(new RecipeItemUpsertDto("Flour", 1.0, "tsp"))
        );
        String body = objectMapper.writeValueAsString(dto);

        RecipeResponse createdResponse = new RecipeResponse(
                99,
                dto.title(),
                dto.instructions(),
                List.of()
        );
        given(recipeService.create(any(RecipeUpsertDto.class)))
                .willReturn(createdResponse);

        mvc.perform(post("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Mock Created"))
                .andExpect(jsonPath("$.id").value(99));
    }
}
