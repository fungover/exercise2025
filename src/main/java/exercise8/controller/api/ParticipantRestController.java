package exercise8.controller.api;

import exercise8.entity.Participant;
import exercise8.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantRestController {

    @Autowired
    private ParticipantService participantService;

    // GET /api/participants - Hämta alla deltagare
    @GetMapping
    public ResponseEntity<List<Participant>> getAllParticipants() {
        List<Participant> participants = participantService.findAll();
        return ResponseEntity.ok(participants);
    }

    // GET /api/participants/{id} - Hämta specifik deltagare
    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        Participant participant = participantService.findById(id);
        return ResponseEntity.ok(participant);
    }

    // POST /api/participants - Skapa ny deltagare
    @PostMapping
    public ResponseEntity<Participant> createParticipant(
            @Valid @RequestBody Participant participant) {
        Participant createdParticipant = participantService.create(participant);
        return new ResponseEntity<>(createdParticipant, HttpStatus.CREATED);
    }

    // PUT /api/participants/{id} - Uppdatera deltagare
    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(
            @PathVariable Long id,
            @Valid @RequestBody Participant participant) {
        Participant updatedParticipant = participantService.update(id, participant);
        return ResponseEntity.ok(updatedParticipant);
    }

    // DELETE /api/participants/{id} - Ta bort deltagare
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable Long id) {
        participantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/participants/patrol/{patrolId} - Deltagare i en scoutkår
    @GetMapping("/patrol/{patrolId}")
    public ResponseEntity<List<Participant>> getParticipantsByPatrol(
            @PathVariable Long patrolId) {
        List<Participant> participants = participantService.findByPatrolId(patrolId);
        return ResponseEntity.ok(participants);
    }

    // GET /api/participants/search?name=xxx - Sök deltagare
    @GetMapping("/search")
    public ResponseEntity<List<Participant>> searchParticipants(@RequestParam String name) {
        List<Participant> participants = participantService.searchByName(name);
        return ResponseEntity.ok(participants);
    }
}

