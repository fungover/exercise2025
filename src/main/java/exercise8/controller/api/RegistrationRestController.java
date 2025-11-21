package exercise8.controller.api;

import exercise8.entity.Registration;
import exercise8.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/registrations")
public class RegistrationRestController {

    @Autowired
    private RegistrationService registrationService;

    // GET /api/registrations - Download all registrations
    @GetMapping
    public ResponseEntity<List<Registration>> getAllRegistrations() {
        List<Registration> registrations = registrationService.findAll();
        return ResponseEntity.ok(registrations);
    }

    // GET /api/registrations/{id} - Get specific registration
    @GetMapping("/{id}")
    public ResponseEntity<Registration> getRegistrationById(@PathVariable Long id) {
        Registration registration = registrationService.findById(id);
        return ResponseEntity.ok(registration);
    }

    // POST /api/registrations - Create new registration
    @PostMapping
    public ResponseEntity<Registration> createRegistration(
            @Valid @RequestBody Registration registration) {
        Registration createdRegistration = registrationService.create(registration);
        return new ResponseEntity<>(createdRegistration, HttpStatus.CREATED);
    }

    // PUT /api/registrations/{id} - Update registration
    @PutMapping("/{id}")
    public ResponseEntity<Registration> updateRegistration(
            @PathVariable Long id,
            @Valid @RequestBody Registration registration) {
        Registration updatedRegistration = registrationService.update(id, registration);
        return ResponseEntity.ok(updatedRegistration);
    }

    // PUT /api/registrations/{id}/confirm - Confirm registration
    @PutMapping("/{id}/confirm")
    public ResponseEntity<Registration> confirmRegistration(@PathVariable Long id) {
        Registration registration = registrationService.confirm(id);
        return ResponseEntity.ok(registration);
    }

    // PUT /api/registrations/{id}/cancel - Cancel registration
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Registration> cancelRegistration(@PathVariable Long id) {
        Registration registration = registrationService.cancel(id);
        return ResponseEntity.ok(registration);
    }

    // DELETE /api/registrations/{id} - Delete registration
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/registrations/event/{eventId} - Registrations for an event
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Registration>> getRegistrationsByEvent(@PathVariable Long eventId) {
        List<Registration> registrations = registrationService.findByEventId(eventId);
        return ResponseEntity.ok(registrations);
    }

    // GET /api/registrations/participant/{participantId} - Registrations for a participant
    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<Registration>> getRegistrationsByParticipant(
            @PathVariable Long participantId) {
        List<Registration> registrations = registrationService.findByParticipantId(participantId);
        return ResponseEntity.ok(registrations);
    }
}
