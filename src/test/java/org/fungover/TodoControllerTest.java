package org.fungover;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fungover.dto.Todo;
import org.fungover.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TodoRepository todoRepository;

    @BeforeEach
    void cleanDb() {
        todoRepository.deleteAll();
    }

    @Test
    void getAllTodos_returnsUnauthorized_whenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "oscar")
    void getAllTodos_returnsEmptyList_whenAuthenticatedAndNoTodosExist() throws Exception {
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void createTodo_returnsUnauthorized_whenNotAuthenticated() throws Exception {
        Todo dto = new Todo(
                null,
                "Test todo",
                "test",
                false,
                LocalDate.of(2025, 11, 30)
        );

        String body = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "oscar")
    void createTodo_returnsCreatedTodo_whenAuthenticated() throws Exception {
        Todo dto = new Todo(
                null,
                "Test todo",
                "test",
                false,
                LocalDate.of(2025, 11, 30)
        );

        String body = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/v1/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Test todo"))
                .andExpect(jsonPath("$.completed").value(false));
    }
}