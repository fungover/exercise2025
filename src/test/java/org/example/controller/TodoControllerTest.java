package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Todo;
import org.example.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void shouldCreateTodoWhenAuthenticated() throws Exception {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setCompleted(false);

        when(todoService.saveTodo(todo)).thenReturn(todo);

        mockMvc.perform(post("/api/todos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnAllTodos() throws Exception {
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(
                new Todo("Task 1"),
                new Todo("Task 2")
        ));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void shouldReturnAllTodosWhenAuthenticated() throws Exception {
        when(todoService.getAllTodos()).thenReturn(Arrays.asList(
                new Todo("Task 1"),
                new Todo("Task 2")
        ));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotCreateTodoWhenUnauthenticated() throws Exception {
        Todo todo = new Todo("Unauthorized Task");

        mockMvc.perform(post("/api/todos")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isUnauthorized());
    }
}