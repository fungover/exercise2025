/**
 * Integration tests for the Quote REST API.
 * These tests use MockMvc together with SpringBootTest to verify:
 * - HTTP endpoint behavior
 * - JSON request/response handling
 * - Spring Security authentication and authorization
 * - Full controller workflow without starting a real HTTP server
 * This class ensures that the REST layer and security layer work together correctly.
 */
package com.example.ex8.quote;

import com.example.ex8.Application;
import com.example.ex8.entities.Quote;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class QuoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * Verifies that an unauthenticated user cannot access the GET /api/quotes endpoint.
     * Expected behavior: Spring Security should return HTTP 401 Unauthorized.
     */
    @Test
    void getQuotes_Unauthenticated_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/quotes"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Verifies that an authenticated user IS allowed to access GET /api/quotes.
     * The request uses @WithMockUser to simulate a logged-in user.
     * Expected behavior: HTTP 200 OK.
     */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getQuotes_Authenticated_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/quotes"))
                .andExpect(status().isOk());
    }

    /**
     * Verifies that an authenticated user can create a new Quote via POST /api/quotes.
     * Sends JSON to the controller and expects the controller to return HTTP 201 Created.
     * Confirms that the endpoint accepts valid JSON and responds with the correct status code.
     */
    @Test
    @WithMockUser(username = "user", roles = "USER")
    void createQuote_Authenticated_ShouldReturn201() throws Exception {
        Quote quote = new Quote();
        quote.setText("test quote from test");

        mockMvc.perform(post("/api/quotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quote)))
                .andExpect(status().isCreated());
    }
}
