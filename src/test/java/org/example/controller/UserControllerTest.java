package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.token.TokenRequest;
import org.example.dto.token.UpdatedToken;
import org.example.dto.user.User;
import org.example.dto.user.UserCheck;
import org.example.repository.TokenRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerMySQLIntegrationTest {

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
  private ObjectMapper objectMapper;

  @Autowired
  private TokenRepository tokenRepository;

  @Test
  void UserRegister() throws Exception {
    User req = new User("User", "password", "user@user.com");
    mockMvc.perform(post("/user/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.refreshToken").exists());
  }
  @Test
  void UserLoginAndTokensUpdate () throws Exception {
    UserCheck req = new UserCheck("password", "user@user.com");
    mockMvc.perform(post("/user/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.refreshToken").exists());
  }

  @Test
  void UserTokenUpdateByRefreshToken() throws Exception {
    var loginBody = new UserCheck("password", "user@user.com");

    var loginResult = mockMvc.perform(post("/user/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginBody))).andReturn();

    String loginJson = loginResult.getResponse().getContentAsString();
    TokenRequest tokenRequest = objectMapper.readValue(loginJson, TokenRequest.class);

    var refreshResult = mockMvc.perform(put("/user/refresh")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(tokenRequest)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists())
            .andReturn();

    String refreshJson = refreshResult.getResponse().getContentAsString();
    UpdatedToken updated = objectMapper.readValue(refreshJson, UpdatedToken.class);

    var tokenEntity = tokenRepository.findByToken(updated.token()).orElseThrow();

    org.junit.jupiter.api.Assertions.assertEquals(updated.token(), tokenEntity.getToken());
    org.junit.jupiter.api.Assertions.assertEquals(tokenRequest.refreshToken(), tokenEntity.getRefreshToken());
  }
}
