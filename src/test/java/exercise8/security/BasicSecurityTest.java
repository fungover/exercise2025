package exercise8.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BasicSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginPage_IsAccessible() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testApiWithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testApiWithValidAuth_ReturnsOk() throws Exception {
        mockMvc.perform(get("/api/events")
                        .with(httpBasic("testadmin", "testpass123")))
                .andExpect(status().isOk());
    }

    @Test
    public void testApiWithWrongPassword_Returns401() throws Exception {
        mockMvc.perform(get("/api/events")
                        .with(httpBasic("testadmin", "wrongpassword")))
                .andExpect(status().isUnauthorized());
    }
}
