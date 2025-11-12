package org.example.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.dto.UserView;
import org.example.config.SecurityConfig;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockitoBean
    UserService users;

    @Test
    void register_returns201_andUserView_whenValidInput() throws Exception {
        when(users.register(any())).thenReturn(new UserView(1L, "alice"));
        var body = """
                {"username":"alice","password":"password123"}
                """;

        var res = mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();

        var json = res.getResponse().getContentAsString();
        var view = om.readValue(json, UserView.class);
        assertThat(view.id()).isEqualTo(1L);
        assertThat(view.username()).isEqualTo("alice");
    }

    @Test
    void register_returns400_whenValidationFails() throws Exception {

        var body = """
                {"username":"","password":""}
                """;

        mvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}
