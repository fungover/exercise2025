package exercise8.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ComprehensiveSecurityTest {

    @Autowired
    private MockMvc mockMvc;


    // Public Endpoints
    @Test
    public void testRootRedirectsToEvents() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/events"));
    }

    @Test
    public void testLoginPageAccessible() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }


    // Protected Web Endpoints - Should redirect
    @Test
    public void testEventsWithoutAuth_RedirectsToLogin() throws Exception {
        mockMvc.perform(get("/events"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser
    public void testEventsWithAuth_ReturnsOk() throws Exception {
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk());
    }


    // Protected API Endpoints - Should return 401
    @Test
    public void testApiEventsWithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testApiAllergensWithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/allergens"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testApiPatrolsWithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/patrols"))
                .andExpect(status().isUnauthorized());
    }


    // Form Login Tests
    @Test
    public void testFormLogin_ValidCredentials_Success() throws Exception {
        mockMvc.perform(formLogin("/login")
                        .user("testadmin")
                        .password("testpass123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/events"));
    }

    @Test
    public void testFormLogin_InvalidPassword_Failure() throws Exception {
        mockMvc.perform(formLogin("/login")
                        .user("testadmin")
                        .password("wrongpassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }


    // Basic Auth Tests (for API)
    @Test
    public void testBasicAuth_ValidCredentials_Success() throws Exception {
        mockMvc.perform(get("/api/events")
                        .with(httpBasic("testadmin", "testpass123")))
                .andExpect(status().isOk());
    }

    @Test
    public void testBasicAuth_InvalidPassword_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/events")
                        .with(httpBasic("testadmin", "wrongpassword")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testBasicAuth_InvalidUsername_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/events")
                        .with(httpBasic("wronguser", "testpass123")))
                .andExpect(status().isUnauthorized());
    }


    // CSRF Tests
    @Test
    @WithMockUser
    public void testPostWithoutCsrf_Forbidden() throws Exception {
        mockMvc.perform(post("/events")
                        .param("name", "Test Event"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void testPostWithCsrf_Success() throws Exception {
        mockMvc.perform(post("/events")
                        .with(csrf())
                        .param("name", "Test Event")
                        .param("startDate", "2026-06-01")
                        .param("endDate", "2026-06-07")
                        .param("location", "Test")
                        .param("maxParticipants", "100"))
                .andExpect(status().is3xxRedirection());
    }
}