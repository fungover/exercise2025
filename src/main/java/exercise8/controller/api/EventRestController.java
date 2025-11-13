package exercise8.controller.api;

import exercise8.entity.Event;
import exercise8.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    @Autowired
    private EventService eventService;

    // GET /api/events - Hämta alla events
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.findAll();
        return ResponseEntity.ok(events);
    }

    // GET /api/events/{id} - Hämta specifikt event
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.findById(id);
        return ResponseEntity.ok(event);
    }

    // POST /api/events - Skapa nytt event
    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        Event createdEvent = eventService.create(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    // PUT /api/events/{id} - Uppdatera event
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody Event event) {
        Event updatedEvent = eventService.update(id, event);
        return ResponseEntity.ok(updatedEvent);
    }

    // DELETE /api/events/{id} - Ta bort event
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/events/upcoming - Hämta kommande events
    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        List<Event> events = eventService.findUpcomingEvents();
        return ResponseEntity.ok(events);
    }

    // GET /api/events/search?name=xxx - Sök events
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(@RequestParam String name) {
        List<Event> events = eventService.searchByName(name);
        return ResponseEntity.ok(events);
    }
}
