package exercise8.controller.api;

import exercise8.entity.Event;
import exercise8.service.EventService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventRestController {

    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

     // Get all events as JSON
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }


     // Get a specific event as JSON
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        try {
            Event event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


     // Download allergy report as JSON
    @GetMapping("/{id}/allergy-report")
    public ResponseEntity<Map<String, List<EventService.ParticipantAllergyInfo>>> getAllergyReport(@PathVariable Long id) {
        try {
            var report = eventService.getAllergyReportForEvent(id);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


     // Export allergi-statistics to CSV
    @GetMapping("/{id}/allergy-report/csv")
    public ResponseEntity<String> exportAllergyReportToCsv(@PathVariable Long id) {
        try {
            Event event = eventService.getEventById(id);
            var report = eventService.getAllergyReportForEvent(id);

            StringBuilder csv = new StringBuilder();
            csv.append("Allergi,Antal,Efternamn,Förnamn,Patrull,Roll\n");

            report.forEach((allergyName, participants) -> {
                boolean first = true;
                for (var participant : participants) {
                    csv.append(String.format("\"%s\",%d,\"%s\",\"%s\",\"%s\",\"%s\"\n",
                            allergyName,
                            first ? participants.size() : 0,
                            participant.getLastName(),
                            participant.getFirstName(),
                            participant.getPatrol(),
                            participant.getRoleGroup() != null ? participant.getRoleGroup() : ""
                    ));
                    first = false;
                }
            });

            String filename = String.format("allergi-rapport-%s-%s.csv",
                    event.getName().replaceAll("[^a-zA-Z0-9]", "-"),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"))
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(new MediaType("text", "csv", java.nio.charset.StandardCharsets.UTF_8))
                    .body(csv.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


     // Export allergy statistics to JSON
    @GetMapping("/{id}/allergy-statistics")
    public ResponseEntity<Map<String, Long>> getAllergyStatistics(@PathVariable Long id) {
        try {
            var statistics = eventService.getAllergyStatisticsForEvent(id);
            return ResponseEntity.ok(statistics);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}