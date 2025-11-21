package exercise8.controller.api;

import exercise8.config.SecurityConfig;
import exercise8.entity.Allergen;
import exercise8.service.AllergenService;
import exercise8.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AllergenRestController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
@DisplayName("Allergen REST Controller Tests")
class AllergenRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AllergenService allergenService;

    private Allergen testAllergen;
    private List<Allergen> allergenList;

    @BeforeEach
    void setUp() {
        testAllergen = new Allergen();
        testAllergen.setId(1L);
        testAllergen.setName("Gluten");

        Allergen allergen2 = new Allergen();
        allergen2.setId(2L);
        allergen2.setName("Laktos");

        allergenList = Arrays.asList(testAllergen, allergen2);
    }


    // GET /api/allergens - List all allergens
    @Test
    @DisplayName("GET /api/allergens - Without Auth - Returns 401")
    void getAllAllergens_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/allergens"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/allergens - With Mock User - Returns OK")
    @WithMockUser
    void getAllAllergens_WithMockUser_ReturnsOk() throws Exception {
        when(allergenService.findAll()).thenReturn(allergenList);

        mockMvc.perform(get("/api/allergens"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Gluten"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Laktos"));

        verify(allergenService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /api/allergens - With Basic Auth - Returns OK")
    void getAllAllergens_WithBasicAuth_ReturnsOk() throws Exception {
        when(allergenService.findAll()).thenReturn(allergenList);

        mockMvc.perform(get("/api/allergens")
                        .with(httpBasic("testadmin", "testpass123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gluten"));
    }


    // GET /api/allergens/{id} - Get specific allergen
    @Test
    @DisplayName("GET /api/allergens/1 - Without Auth - Returns 401")
    void getAllergenById_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/allergens/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/allergens/1 - With Auth - Returns Allergen")
    @WithMockUser
    void getAllergenById_WithAuth_ReturnsAllergen() throws Exception {
        when(allergenService.findById(1L)).thenReturn(testAllergen);

        mockMvc.perform(get("/api/allergens/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gluten"));

        verify(allergenService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("GET /api/allergens/999 - Not Found - Returns 404")
    @WithMockUser
    void getAllergenById_NotFound_Returns404() throws Exception {
        when(allergenService.findById(999L))
                .thenThrow(new ResourceNotFoundException("Allergen not found"));

        mockMvc.perform(get("/api/allergens/999"))
                .andExpect(status().isNotFound());
    }


    // POST /api/allergens - Create new allergen
    @Test
    @DisplayName("POST /api/allergens - Without Auth - Returns 401")
    void createAllergen_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(post("/api/allergens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nötter\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/allergens - With Auth - Returns 201 Created")
    @WithMockUser
    void createAllergen_WithAuth_ReturnsCreated() throws Exception {
        Allergen newAllergen = new Allergen();
        newAllergen.setId(3L);
        newAllergen.setName("Nötter");

        when(allergenService.create(any(Allergen.class))).thenReturn(newAllergen);

        mockMvc.perform(post("/api/allergens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nötter\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("Nötter"));

        verify(allergenService, times(1)).create(any(Allergen.class));
    }

    @Test
    @DisplayName("POST /api/allergens - With Basic Auth - Returns 201 Created")
    void createAllergen_WithBasicAuth_ReturnsCreated() throws Exception {
        Allergen newAllergen = new Allergen();
        newAllergen.setId(3L);
        newAllergen.setName("Nötter");

        when(allergenService.create(any(Allergen.class))).thenReturn(newAllergen);

        mockMvc.perform(post("/api/allergens")
                        .with(httpBasic("testadmin", "testpass123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nötter\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Nötter"));
    }


    // PUT /api/allergens/{id} - Update allergen
    @Test
    @DisplayName("PUT /api/allergens/1 - Without Auth - Returns 401")
    void updateAllergen_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(put("/api/allergens/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Gluten (uppdaterad)\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PUT /api/allergens/1 - With Auth - Returns 200 OK")
    @WithMockUser
    void updateAllergen_WithAuth_ReturnsOk() throws Exception {
        Allergen updatedAllergen = new Allergen();
        updatedAllergen.setId(1L);
        updatedAllergen.setName("Gluten (uppdaterad)");

        when(allergenService.update(eq(1L), any(Allergen.class))).thenReturn(updatedAllergen);

        mockMvc.perform(put("/api/allergens/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Gluten (uppdaterad)\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Gluten (uppdaterad)"));

        verify(allergenService, times(1)).update(eq(1L), any(Allergen.class));
    }

    @Test
    @DisplayName("PUT /api/allergens/1 - With Basic Auth - Returns 200 OK")
    void updateAllergen_WithBasicAuth_ReturnsOk() throws Exception {
        Allergen updatedAllergen = new Allergen();
        updatedAllergen.setId(1L);
        updatedAllergen.setName("Gluten (uppdaterad)");

        when(allergenService.update(eq(1L), any(Allergen.class))).thenReturn(updatedAllergen);

        mockMvc.perform(put("/api/allergens/1")
                        .with(httpBasic("testadmin", "testpass123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Gluten (uppdaterad)\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gluten (uppdaterad)"));
    }

    @Test
    @DisplayName("PUT /api/allergens/999 - Not Found - Returns 404")
    @WithMockUser
    void updateAllergen_NotFound_Returns404() throws Exception {
        when(allergenService.update(eq(999L), any(Allergen.class)))
                .thenThrow(new ResourceNotFoundException("Allergen not found"));

        mockMvc.perform(put("/api/allergens/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test\"}"))
                .andExpect(status().isNotFound());
    }


    // DELETE /api/allergens/{id} - Delete allergen
    @Test
    @DisplayName("DELETE /api/allergens/1 - Without Auth - Returns 401")
    void deleteAllergen_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(delete("/api/allergens/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("DELETE /api/allergens/1 - With Auth - Returns 204 No Content")
    @WithMockUser
    void deleteAllergen_WithAuth_ReturnsNoContent() throws Exception {
        doNothing().when(allergenService).delete(1L);

        mockMvc.perform(delete("/api/allergens/1"))
                .andExpect(status().isNoContent());

        verify(allergenService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/allergens/1 - With Basic Auth - Returns 204 No Content")
    void deleteAllergen_WithBasicAuth_ReturnsNoContent() throws Exception {
        doNothing().when(allergenService).delete(1L);

        mockMvc.perform(delete("/api/allergens/1")
                        .with(httpBasic("testadmin", "testpass123")))
                .andExpect(status().isNoContent());

        verify(allergenService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/allergens/999 - Not Found - Returns 404")
    @WithMockUser
    void deleteAllergen_NotFound_Returns404() throws Exception {
        doThrow(new ResourceNotFoundException("Allergen not found"))
                .when(allergenService).delete(999L);

        mockMvc.perform(delete("/api/allergens/999"))
                .andExpect(status().isNotFound());
    }
}