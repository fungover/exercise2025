package org.example.controllers;

import org.example.config.CustomAuthFailureHandler;
import org.example.config.MustChangePasswordAuthProvider;
import org.example.config.SecurityConfig;
import org.example.entities.User;
import org.example.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean private MustChangePasswordAuthProvider mustChangePasswordAuthProvider;
    @MockitoBean private CustomAuthFailureHandler customAuthFailureHandler;
    @MockitoBean private UserService userService;

    @Test
    void ShowRegistrationFormShouldReturnView() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void RegisterUserShouldRedirectToLogin() throws Exception {
        doNothing().when(userService).registerUser(any(User.class));

        mockMvc.perform(post("/register")
                        .param("username", "marcus")
                        .param("password", "pw123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = "marcus")
    void ShowFormShouldReturnView() throws Exception {
        mockMvc.perform(get("/change-password")
                        .param("username", "marcus"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password"))
                .andExpect(model().attribute("username", "marcus"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void AdminChangePasswordSuccess() throws Exception {

        doNothing().when(userService)
                .updatePassword(anyString(), anyString(), anyString(), anyString());

        mockMvc.perform(put("/change-password")
                        .param("_method", "put")
                        .param("username", "admin")
                        .param("currentPassword", "adminpassword")
                        .param("newPassword", "new123")
                        .param("confirmPassword", "new123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "marcus", roles = "ADMIN")
    void ChangePassword_Failure_ShowsError() throws Exception {

        doThrow(new IllegalArgumentException("Incorrect password"))
                .when(userService)
                .updatePassword(anyString(), anyString(), anyString(), anyString());

        mockMvc.perform(put("/change-password")
                        .param("_method", "put")
                        .param("username", "admin")
                        .param("currentPassword", "wrong")
                        .param("newPassword", "new123")
                        .param("confirmPassword", "new123"))
                .andExpect(status().isOk())
                .andExpect(view().name("change-password"))
                .andExpect(model().attribute("error", "Incorrect password"));
    }
}
