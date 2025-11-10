package org.example.controllers;

import org.example.DTO.CatchYearDTO;
import org.example.DTO.CreateCatchDTO;
import org.example.config.CustomAuthFailureHandler;
import org.example.config.MustChangePasswordAuthProvider;
import org.example.config.SecurityConfig;
import org.example.entities.Catch;
import org.example.repository.CatchRepository;
import org.example.services.CatchService;
import org.example.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatchController.class)
@Import(SecurityConfig.class)
class CatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /** Security beans from your SecurityConfig */
    @MockitoBean private MustChangePasswordAuthProvider mustChangePasswordAuthProvider;
    @MockitoBean private CustomAuthFailureHandler customAuthFailureHandler;
    @MockitoBean private UserService userService;

    /** Controller dependencies */
    @MockitoBean private CatchService catchService;
    @MockitoBean private CatchRepository catchRepository;


    @Test
    @WithMockUser(roles = "ADMIN")
    void GetAllCatches_shouldReturn200AndJson() throws Exception {
        when(catchService.getAllCatches()).thenReturn(Collections.emptyList());
        when(catchService.countCatches()).thenReturn(0L);

        mockMvc.perform(get("/api/catches"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Count", "0"))
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void GetCatchById_found_shouldReturn200() throws Exception {
        Catch fish = new Catch();
        fish.setId(1L);
        fish.setSpecies("Pike");
        fish.setWeight(4.2);
        fish.setLength(55.0);

        when(catchService.getCatchById(1L)).thenReturn(Optional.of(fish));

        mockMvc.perform(get("/api/catches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.species").value("Pike"))
                .andExpect(jsonPath("$.weight").value(4.2))
                .andExpect(jsonPath("$.length").value(55.0));
    }

    @Test
    @WithMockUser(roles = "USER")
    void GetCatchById_notFound_shouldReturn404() throws Exception {
        when(catchService.getCatchById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/catches/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void GetCatchesOrderedByWeight_validAsc_shouldReturn200() throws Exception {

        OffsetDateTime now = OffsetDateTime.now();

        CatchYearDTO dto = new CatchYearDTO(
                1L,
                "Pike",
                55.0,
                4.2,
                now,
                2025
        );

        when(catchService.getCatchesOrderedByWeight(true))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/catches/weight?order=asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].species").value("Pike"))
                .andExpect(jsonPath("$[0].length").value(55.0))
                .andExpect(jsonPath("$[0].weight").value(4.2))
                .andExpect(jsonPath("$[0].year").value(2025));
    }

    @Test
    @WithMockUser(roles = "USER")
    void GetCatchesOrderedByWeight_invalidOrder_shouldReturn400() throws Exception {
        mockMvc.perform(get("/api/catches/weight?order=hello"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Invalid order parameter. Must be either 'asc' or 'desc'."));
    }

    @Test
    @WithMockUser(roles = "USER")
    void CreateCatch_valid_shouldReturn201() throws Exception {

        Catch saved = new Catch();
        saved.setId(99L);
        saved.setSpecies("Pike");
        saved.setWeight(5.0);
        saved.setLength(80.0);

        when(catchService.createCatch(any(CreateCatchDTO.class))).thenReturn(saved);

        String jsonBody = """
                {
                  "species": "Pike",
                  "weight": 5.0,
                  "length": 80.0
                }
                """;

        mockMvc.perform(post("/api/catches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/catches/99"))
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.species").value("Pike"))
                .andExpect(jsonPath("$.weight").value(5.0))
                .andExpect(jsonPath("$.length").value(80.0));
    }

    @Test
    @WithMockUser(roles = "USER")
    void CreateCatch_invalid_shouldReturn400() throws Exception {

        mockMvc.perform(post("/api/catches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void DeleteCatch_admin_shouldReturn204() throws Exception {
        when(catchRepository.existsById(1L)).thenReturn(true);
        doNothing().when(catchRepository).deleteById(1L);

        mockMvc.perform(delete("/api/catches/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    void DeleteCatchUserShouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/catches/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void DeleteCatchUnauthenticatedShouldReturn401() throws Exception {
        mockMvc.perform(delete("/api/catches/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void DeleteCatchNotFoundShouldReturn404() throws Exception {
        when(catchRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/catches/1"))
                .andExpect(status().isNotFound());
    }
}
