package exercise8.service;

import exercise8.entity.Allergen;
import exercise8.entity.Event;
import exercise8.entity.Participant;
import exercise8.entity.Registration;
import exercise8.exception.ResourceNotFoundException;
import exercise8.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


     // Retrieving all events
    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }


     // Get a specific event
    @Transactional(readOnly = true)
    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }


     // Saving an event
    @Transactional
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }


     // Delete an event
    @Transactional
    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));

        // Kontrollera om eventet har registreringar
        if (event.getRegistrations() != null && !event.getRegistrations().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete event with existing registrations. " +
                            "Please delete all registrations first or cancel them.");
        }

        eventRepository.delete(event);
    }


     // Retrieves allergy report for a specific event
     // Groups participants by allergy type
    @Transactional(readOnly = true)
    public Map<String, List<ParticipantAllergyInfo>> getAllergyReportForEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Map: allergy-name -> list of participants
        Map<String, List<ParticipantAllergyInfo>> allergyReport = new LinkedHashMap<>();

        // Review all registered participants
        for (Registration registration : event.getRegistrations()) {
            Participant participant = registration.getParticipant();

            // Review the participant's allergies
            for (Allergen allergen : participant.getAllergens()) {
                String allergyName = allergen.getName();

                // Create list if it doesn't exist
                allergyReport.putIfAbsent(allergyName, new ArrayList<>());

                // Add participants to the list
                allergyReport.get(allergyName).add(
                        new ParticipantAllergyInfo(
                                participant.getLastName(),
                                participant.getFirstName(),
                                participant.getPatrol() != null ? participant.getPatrol().getName() : "Ingen patrull",
                                participant.getRoleGroup()
                        )
                );
            }
        }

        // Sort allergies alphabetically
        Map<String, List<ParticipantAllergyInfo>> sortedReport = new TreeMap<>(allergyReport);

        // Sort each list alphabetically by last name
        sortedReport.values().forEach(list ->
                list.sort(Comparator.comparing(ParticipantAllergyInfo::getLastName)
                        .thenComparing(ParticipantAllergyInfo::getFirstName))
        );

        return sortedReport;
    }


     // Counts number of participants per allergy for an event
    @Transactional(readOnly = true)
    public Map<String, Long> getAllergyStatisticsForEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Map<String, Long> statistics = new TreeMap<>();

        for (Registration registration : event.getRegistrations()) {
            for (Allergen allergen : registration.getParticipant().getAllergens()) {
                statistics.merge(allergen.getName(), 1L, Long::sum);
            }
        }

        return statistics;
    }


     // Inner class to hold participants-info
    public static class ParticipantAllergyInfo {
        private final String lastName;
        private final String firstName;
        private final String patrol;
        private final String roleGroup;

        public ParticipantAllergyInfo(String lastName, String firstName, String patrol, String roleGroup) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.patrol = patrol;
            this.roleGroup = roleGroup;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getFullName() {
            return lastName + " " + firstName;
        }

        public String getPatrol() {
            return patrol;
        }

        public String getRoleGroup() {
            return roleGroup;
        }
    }
}