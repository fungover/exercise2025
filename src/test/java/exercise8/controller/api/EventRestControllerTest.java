package exercise8.controller.api;

import exercise8.config.SecurityConfig;
import exercise8.entity.Event;
import exercise8.service.EventService;
import exercise8.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventRestController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
@DisplayName("Event REST Controller Tests")
class EventRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        testEvent = new Event();
        testEvent.setId(1L);
        testEvent.setName("Sommarläger 2026");
        testEvent.setStartDate(LocalDate.of(2026, 7, 10));
        testEvent.setEndDate(LocalDate.of(2026, 7, 17));
        testEvent.setLocation("Öland");
        testEvent.setMaxParticipants(150);
    }


    // GET /api/events - List all events
    @Test
    @DisplayName("GET /api/events - Without Auth - Returns 401")
    void getAllEvents_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/events"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/events - With Mock User - Returns OK")
    @WithMockUser
    void getAllEvents_WithMockUser_ReturnsOk() throws Exception {
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(testEvent));

        mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Sommarläger 2026"))
                .andExpect(jsonPath("$[0].location").value("Öland"))
                .andExpect(jsonPath("$[0].maxParticipants").value(150));
    }

    @Test
    @DisplayName("GET /api/events - With Basic Auth - Returns OK")
    void getAllEvents_WithBasicAuth_ReturnsOk() throws Exception {
        when(eventService.getAllEvents()).thenReturn(Arrays.asList(testEvent));

        mockMvc.perform(get("/api/events")
                        .with(httpBasic("testadmin", "testpass123")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Sommarläger 2026"));
    }

    @Test
    @DisplayName("GET /api/events - With Wrong Password - Returns 401")
    void getAllEvents_WithWrongPassword_Returns401() throws Exception {
        mockMvc.perform(get("/api/events")
                        .with(httpBasic("testadmin", "wrongpassword")))
                .andExpect(status().isUnauthorized());
    }


    // GET /api/events/{id} - Get specific event
     @Test
    @DisplayName("GET /api/events/1 - Without Auth - Returns 401")
    void getEvent_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/events/1 - With Auth - Returns Event")
    @WithMockUser
    void getEvent_WithAuth_ReturnsEvent() throws Exception {
        when(eventService.getEventById(1L)).thenReturn(testEvent);

        mockMvc.perform(get("/api/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Sommarläger 2026"))
                .andExpect(jsonPath("$.location").value("Öland"));
    }

    @Test
    @DisplayName("GET /api/events/999 - Event Not Found - Returns 404")
    @WithMockUser
    void getEvent_NotFound_Returns404() throws Exception {
        when(eventService.getEventById(999L))
                .thenThrow(new ResourceNotFoundException("Event not found"));

        mockMvc.perform(get("/api/events/999"))
                .andExpect(status().isNotFound());
    }


    // GET /api/events/{id}/allergy-report
    @Test
    @DisplayName("GET /api/events/1/allergy-report - Without Auth - Returns 401")
    void getAllergyReport_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/events/1/allergy-report"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/events/1/allergy-report - With Auth - Returns Report")
    @WithMockUser
    void getAllergyReport_WithAuth_ReturnsReport() throws Exception {
        Map<String, List<EventService.ParticipantAllergyInfo>> mockReport = new HashMap<>();
        when(eventService.getAllergyReportForEvent(1L)).thenReturn(mockReport);
        when(eventService.getEventById(1L)).thenReturn(testEvent);

        mockMvc.perform(get("/api/events/1/allergy-report"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    // GET /api/events/{id}/allergy-report/csv
    @Test
    @DisplayName("GET /api/events/1/allergy-report/csv - Without Auth - Returns 401")
    void exportAllergyReportCsv_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/events/1/allergy-report/csv"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/events/1/allergy-report/csv - With Auth - Returns CSV")
    @WithMockUser
    void exportAllergyReportCsv_WithAuth_ReturnsCsv() throws Exception {
        Map<String, List<EventService.ParticipantAllergyInfo>> mockReport = new HashMap<>();
        when(eventService.getAllergyReportForEvent(1L)).thenReturn(mockReport);
        when(eventService.getEventById(1L)).thenReturn(testEvent);

        mockMvc.perform(get("/api/events/1/allergy-report/csv"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Content-Disposition"));
    }


    // GET /api/events/{id}/allergy-statistics
    @Test
    @DisplayName("GET /api/events/1/allergy-statistics - Without Auth - Returns 401")
    void getAllergyStatistics_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/events/1/allergy-statistics"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/events/1/allergy-statistics - With Auth - Returns Statistics")
    @WithMockUser
    void getAllergyStatistics_WithAuth_ReturnsStatistics() throws Exception {
        Map<String, Long> mockStats = new HashMap<>();
        mockStats.put("Gluten", 5L);
        mockStats.put("Laktos", 3L);

        when(eventService.getAllergyStatisticsForEvent(1L)).thenReturn(mockStats);

        mockMvc.perform(get("/api/events/1/allergy-statistics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Gluten").value(5))
                .andExpect(jsonPath("$.Laktos").value(3));
    }
}
