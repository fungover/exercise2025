package org.example.controller;

import org.example.entity.UserEntity;
import org.example.repository.NoteRepository;
import org.example.repository.UserRepository;
import org.example.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerTest {

  @Container
  static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.1")
          .withDatabaseName("testdb")
          .withUsername("test")
          .withPassword("test");

  @DynamicPropertySource
  static void registerMySQLProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", mysql::getJdbcUrl);
    registry.add("spring.datasource.username", mysql::getUsername);
    registry.add("spring.datasource.password", mysql::getPassword);
    registry.add("spring.datasource.driver-class-name", mysql::getDriverClassName);
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private NoteRepository noteRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @BeforeEach
  void setUp() {
    noteRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  void userWithValidTokenCheckHisNotes() throws Exception {
    UserEntity user = new UserEntity();
    user.setName("Test User");
    user.setEmail("test@email.com");
    user = userRepository.save(user);

    String token = jwtUtil.generateToken(user.getEmail(), 10000000L);

    mockMvc.perform(get("/api/notes/user")
                    .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
  }

  @Test
  void UserCanCreateNote() throws Exception {
    UserEntity user = new UserEntity();
    user.setName("Test User");
    user.setEmail("test@email.com");
    userRepository.save(user);

    String token = jwtUtil.generateToken("test@email.com", 100000L);

    String jsonContent = "{\"value\": \"Test note content\"}";

    mockMvc.perform(post("/api/notes")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent)
                    .with(csrf()))
            .andExpect(status().isCreated());
  }

  @Test
  void UserCantCreateNoteIfTokenHasExpired() throws Exception {
    UserEntity user = new UserEntity();
    user.setName("Test User");
    user.setEmail("test@email.com");
    userRepository.save(user);

    String token = jwtUtil.generateToken("test@email.com", 1L);

    String jsonContent = "{\"value\": \"Test note content\"}";

    mockMvc.perform(post("/api/notes")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent)
                    .with(csrf()))
            .andExpect(status().isUnauthorized());
  }

  @Test
  void UserCanDeleteHisOwnNote() throws Exception {
    UserEntity user = new UserEntity();
    user.setName("Test User");
    user.setEmail("test@email.com");
    userRepository.save(user);

    String token = jwtUtil.generateToken("test@email.com", 100000L);

    String jsonContent = "{\"value\": \"Test note content\"}";

    mockMvc.perform(post("/api/notes")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent)
                    .with(csrf()))
            .andExpect(status().isCreated());

    mockMvc.perform(delete("/api/notes/2")
            .header("Authorization", "Bearer " + token)
            .with(csrf()))
            .andExpect(status().isOk());
  }

  @Test
  void UserCantDeleteNoteWhatNotBelongsUserAndUsingToken() throws Exception {
    UserEntity user = new UserEntity();
    user.setName("Test User");
    user.setEmail("test@email.com");
    userRepository.save(user);

    UserEntity user2 = new UserEntity();
    user2.setName("Test User 2");
    user2.setEmail("test2@email.com");
    userRepository.save(user2);

    String token = jwtUtil.generateToken("test@email.com", 100000L);
    String token2 = jwtUtil.generateToken("test2@email.com", 100000L);

    String jsonContent = "{\"value\": \"Test note content\"}";

    mockMvc.perform(post("/api/notes")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonContent)
                    .with(csrf()))
            .andExpect(status().isCreated());

    mockMvc.perform(delete("/api/notes/3")
                    .header("Authorization", "Bearer " + token2)
                    .with(csrf()))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").exists())
                    .andExpect(jsonPath("$.status").exists());
  }
}