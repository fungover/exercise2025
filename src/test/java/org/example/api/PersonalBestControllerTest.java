package org.example.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.dto.PersonalBestCreate;
import org.example.api.dto.PersonalBestView;
import org.example.config.SecurityConfig;
import org.example.service.PersonalBestService;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PersonalBestController.class)
@Import(SecurityConfig.class)
class PersonalBestControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockitoBean
    PersonalBestService prs;
    @MockitoBean
    UserService users;

    @Test
    @WithMockUser(username = "alice")
    void getMyPrs_returnsList_forAuthenticatedUser() throws Exception {
        when(users.requireUserId("alice")).thenReturn(10L);
        var view = new PersonalBestView(1L, 2L, "Bench Press", 5, new BigDecimal("100.0"), LocalDate.of(2025, 11, 1));
        when(prs.listForUser(10L)).thenReturn(List.of(view));

        var res = mvc.perform(get("/api/pr"))
                .andExpect(status().isOk())
                .andReturn();

        var list = om.readValue(res.getResponse().getContentAsString(),
                new TypeReference<List<PersonalBestView>>() {
                });
        assertThat(list).hasSize(1);
        assertThat(list.get(0).exerciseName()).isEqualTo("Bench Press");
    }

    @Test
    @WithMockUser(username = "alice")
    void upsert_returns201_andView_forAuthenticatedUser() throws Exception {
        when(users.requireUserId("alice")).thenReturn(10L);
        var created = new PersonalBestView(11L, 2L, "Bench Press", 3, new BigDecimal("110.0"), LocalDate.of(2025, 11, 2));
        when(prs.upsert(eq(10L), any(PersonalBestCreate.class))).thenReturn(created);

        var body = """
                {"exerciseId":2,"reps":3,"weightKg":110.0,"achievedOn":"2025-11-02"}
                """;

        var res = mvc.perform(post("/api/pr")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();

        var view = om.readValue(res.getResponse().getContentAsString(), PersonalBestView.class);
        assertThat(view.id()).isEqualTo(11L);
        assertThat(view.exerciseName()).isEqualTo("Bench Press");
    }

    @Test
    @WithMockUser(username = "alice")
    void delete_returns204_forAuthenticatedUser() throws Exception {
        when(users.requireUserId("alice")).thenReturn(10L);

        mvc.perform(delete("/api/pr/{id}", 11))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void getMyPrs_shouldReturn401_whenAnonymous() throws Exception {
        // ingen @WithMockUser => anonym
        mvc.perform(get("/api/pr"))
                .andExpect(status().isUnauthorized()); // 401
    }

}
