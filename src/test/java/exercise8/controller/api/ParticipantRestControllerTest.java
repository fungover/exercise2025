package exercise8.controller.api;

import exercise8.config.SecurityConfig;
import exercise8.entity.Participant;
import exercise8.service.ParticipantService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParticipantRestController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
@DisplayName("Participant REST Controller Tests")
class ParticipantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ParticipantService participantService;

    private Participant testParticipant;
    private List<Participant> participantList;

    @BeforeEach
    void setUp() {
        testParticipant = new Participant();
        testParticipant.setId(1L);
        testParticipant.setFirstName("Anna");
        testParticipant.setLastName("Andersson");
        testParticipant.setEmail("anna@example.com");

        Participant participant2 = new Participant();
        participant2.setId(2L);
        participant2.setFirstName("Erik");
        participant2.setLastName("Eriksson");
        participant2.setEmail("erik@example.com");

        participantList = Arrays.asList(testParticipant, participant2);
    }


    // GET /api/participants - List all participants
    @Test
    @DisplayName("GET /api/participants - Without Auth - Returns 401")
    void getAllParticipants_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/participants"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/participants - With Mock User - Returns OK")
    @WithMockUser
    void getAllParticipants_WithMockUser_ReturnsOk() throws Exception {
        when(participantService.findAll()).thenReturn(participantList);

        mockMvc.perform(get("/api/participants"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Anna"))
                .andExpect(jsonPath("$[0].lastName").value("Andersson"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Erik"));

        verify(participantService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /api/participants - With Basic Auth - Returns OK")
    void getAllParticipants_WithBasicAuth_ReturnsOk() throws Exception {
        when(participantService.findAll()).thenReturn(participantList);

        mockMvc.perform(get("/api/participants")
                        .with(httpBasic("testadmin", "testpass123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Anna"));
    }


    // GET /api/participants/{id} - Get specific participant
    @Test
    @DisplayName("GET /api/participants/1 - Without Auth - Returns 401")
    void getParticipantById_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/participants/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/participants/1 - With Auth - Returns Participant")
    @WithMockUser
    void getParticipantById_WithAuth_ReturnsParticipant() throws Exception {
        when(participantService.findById(1L)).thenReturn(testParticipant);

        mockMvc.perform(get("/api/participants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Anna"))
                .andExpect(jsonPath("$.lastName").value("Andersson"))
                .andExpect(jsonPath("$.email").value("anna@example.com"));

        verify(participantService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("GET /api/participants/999 - Not Found - Returns 404")
    @WithMockUser
    void getParticipantById_NotFound_Returns404() throws Exception {
        when(participantService.findById(999L))
                .thenThrow(new ResourceNotFoundException("Participant not found"));

        mockMvc.perform(get("/api/participants/999"))
                .andExpect(status().isNotFound());
    }


    // POST /api/participants - Create new participant
    @Test
    @DisplayName("POST /api/participants - Without Auth - Returns 401")
    void createParticipant_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(post("/api/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Lisa\",\"lastName\":\"Larsson\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("POST /api/participants - With Auth - Returns 201 Created")
    @WithMockUser
    void createParticipant_WithAuth_ReturnsCreated() throws Exception {
        Participant newParticipant = new Participant();
        newParticipant.setId(3L);
        newParticipant.setFirstName("Lisa");
        newParticipant.setLastName("Larsson");
        newParticipant.setEmail("lisa@example.com");

        when(participantService.create(any(Participant.class))).thenReturn(newParticipant);

        mockMvc.perform(post("/api/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Lisa\",\"lastName\":\"Larsson\",\"email\":\"lisa@example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.firstName").value("Lisa"))
                .andExpect(jsonPath("$.lastName").value("Larsson"));

        verify(participantService, times(1)).create(any(Participant.class));
    }

    @Test
    @DisplayName("POST /api/participants - With Basic Auth - Returns 201 Created")
    void createParticipant_WithBasicAuth_ReturnsCreated() throws Exception {
        Participant newParticipant = new Participant();
        newParticipant.setId(3L);
        newParticipant.setFirstName("Lisa");
        newParticipant.setLastName("Larsson");

        when(participantService.create(any(Participant.class))).thenReturn(newParticipant);

        mockMvc.perform(post("/api/participants")
                        .with(httpBasic("testadmin", "testpass123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Lisa\",\"lastName\":\"Larsson\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Lisa"));
    }


    // PUT /api/participants/{id} - Update participants
    @Test
    @DisplayName("PUT /api/participants/1 - Without Auth - Returns 401")
    void updateParticipant_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(put("/api/participants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Anna\",\"lastName\":\"Svensson\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PUT /api/participants/1 - With Auth - Returns 200 OK")
    @WithMockUser
    void updateParticipant_WithAuth_ReturnsOk() throws Exception {
        Participant updatedParticipant = new Participant();
        updatedParticipant.setId(1L);
        updatedParticipant.setFirstName("Anna");
        updatedParticipant.setLastName("Svensson");
        updatedParticipant.setEmail("anna.svensson@example.com");

        when(participantService.update(eq(1L), any(Participant.class))).thenReturn(updatedParticipant);

        mockMvc.perform(put("/api/participants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Anna\",\"lastName\":\"Svensson\",\"email\":\"anna.svensson@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.lastName").value("Svensson"));

        verify(participantService, times(1)).update(eq(1L), any(Participant.class));
    }

    @Test
    @DisplayName("PUT /api/participants/1 - With Basic Auth - Returns 200 OK")
    void updateParticipant_WithBasicAuth_ReturnsOk() throws Exception {
        Participant updatedParticipant = new Participant();
        updatedParticipant.setId(1L);
        updatedParticipant.setFirstName("Anna");
        updatedParticipant.setLastName("Svensson");

        when(participantService.update(eq(1L), any(Participant.class))).thenReturn(updatedParticipant);

        mockMvc.perform(put("/api/participants/1")
                        .with(httpBasic("testadmin", "testpass123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Anna\",\"lastName\":\"Svensson\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Svensson"));
    }

    @Test
    @DisplayName("PUT /api/participants/999 - Not Found - Returns 404")
    @WithMockUser
    void updateParticipant_NotFound_Returns404() throws Exception {
        when(participantService.update(eq(999L), any(Participant.class)))
                .thenThrow(new ResourceNotFoundException("Participant not found"));

        mockMvc.perform(put("/api/participants/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Test\"}"))
                .andExpect(status().isNotFound());
    }


    // DELETE /api/participants/{id} - Delete participants
    @Test
    @DisplayName("DELETE /api/participants/1 - Without Auth - Returns 401")
    void deleteParticipant_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(delete("/api/participants/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("DELETE /api/participants/1 - With Auth - Returns 204 No Content")
    @WithMockUser
    void deleteParticipant_WithAuth_ReturnsNoContent() throws Exception {
        doNothing().when(participantService).delete(1L);

        mockMvc.perform(delete("/api/participants/1"))
                .andExpect(status().isNoContent());

        verify(participantService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/participants/1 - With Basic Auth - Returns 204 No Content")
    void deleteParticipant_WithBasicAuth_ReturnsNoContent() throws Exception {
        doNothing().when(participantService).delete(1L);

        mockMvc.perform(delete("/api/participants/1")
                        .with(httpBasic("testadmin", "testpass123")))
                .andExpect(status().isNoContent());

        verify(participantService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("DELETE /api/participants/999 - Not Found - Returns 404")
    @WithMockUser
    void deleteParticipant_NotFound_Returns404() throws Exception {
        doThrow(new ResourceNotFoundException("Participant not found"))
                .when(participantService).delete(999L);

        mockMvc.perform(delete("/api/participants/999"))
                .andExpect(status().isNotFound());
    }


    // GET /api/participants/patrol/{patrolId}
    @Test
    @DisplayName("GET /api/participants/patrol/1 - Without Auth - Returns 401")
    void getParticipantsByPatrol_WithoutAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/participants/patrol/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET /api/participants/patrol/1 - With Auth - Returns Participants")
    @WithMockUser
    void getParticipantsByPatrol_WithAuth_ReturnsParticipants() throws Exception {
        when(participantService.findByPatrolId(1L)).thenReturn(participantList);

        mockMvc.perform(get("/api/participants/patrol/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Anna"))
                .andExpect(jsonPath("$[1].firstName").value("Erik"));

        verify(participantService, times(1)).findByPatrolId(1L);
    }

    @Test
    @DisplayName("GET /api/participants/patrol/1 - With Basic Auth - Returns Participants")
    void getParticipantsByPatrol_WithBasicAuth_ReturnsParticipants() throws Exception {
        when(participantService.findByPatrolId(1L)).thenReturn(participantList);

        mockMvc.perform(get("/api/participants/patrol/1")
                        .with(httpBasic("testadmin", "testpass123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Anna"));
    }
}