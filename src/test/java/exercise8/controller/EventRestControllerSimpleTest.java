package exercise8.controller;

import exercise8.controller.api.EventRestController;
import exercise8.entity.Event;
import exercise8.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventRestController.class)
@ActiveProfiles("test")
class EventRestControllerSimpleTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setName("Sommarläger");
        testEvent.setStartDate(LocalDate.of(2026, 7, 10));
        testEvent.setEndDate(LocalDate.of(2026, 7, 17));
        testEvent.setLocation("Öland");
        testEvent.setMaxParticipants(150);
    }

    @Test
    @WithMockUser
    void testGetAllEvents_ReturnsOk() throws Exception {
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(testEvent));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sommarläger"));
    }

    @Test
    void testGetAllEvents_WithoutAuth_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void testGetEvent_ReturnsEvent() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(testEvent);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sommarläger"))
                .andExpect(jsonPath("$.location").value("Öland"));
    }
}
