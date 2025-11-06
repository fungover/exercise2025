package org.example.api.director;

import jakarta.servlet.http.HttpServletRequest;
import org.example.api.security.auth.ApiKeyAuthentication;
import org.example.api.security.service.AuthenticationService;
import org.example.config.security.SecurityConfig;
import org.example.entities.Director;
import org.example.service.director.DirectorService;
import org.example.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DirectorController.class)
@Import(SecurityConfig.class)
public class DirectorControllerTest {

    @MockitoBean
    private DirectorService directorService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthenticationService authenticationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAdminCanAddDirector() throws Exception {
        Director responseDirector = new Director("James", "Gunn", List.of());

        when(directorService.addDirector(any())).thenReturn(responseDirector);

        when(authenticationService.getAuthentication(any(HttpServletRequest.class)))
                .thenReturn(new ApiKeyAuthentication("secret", AuthorityUtils.createAuthorityList("ROLE_ADMIN")));

        mockMvc.perform(post("/api/director")
                        .header("X-API-KEY", "secret")
                        .contentType("application/json")
                        .content("""
                    {"firstName":"James","lastName":"Gunn","movies":[]}
                """))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testUserCanNotAddDirector() throws Exception {
        Director responseDirector = new Director("James", "Gunn", List.of());

        when(directorService.addDirector(any())).thenReturn(responseDirector);

        when(authenticationService.getAuthentication(any(HttpServletRequest.class)))
                .thenReturn(new ApiKeyAuthentication("secret", AuthorityUtils.createAuthorityList("ROLE_USER")));

        mockMvc.perform(post("/api/director")
                        .header("X-API-KEY", "secret")
                        .contentType("application/json")
                        .content("""
                    {"firstName":"James","lastName":"Gunn","movies":[]}
                """))
                .andExpect(status().isForbidden());
    }

}
