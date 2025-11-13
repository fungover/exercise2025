package exercise8.service;

import exercise8.entity.Event;
import exercise8.exception.ResourceNotFoundException;
import exercise8.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Hämta alla events
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    // Hämta event via ID
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
    }

    // Skapa nytt event
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    // Uppdatera event
    public Event update(Long id, Event eventDetails) {
        Event event = findById(id);

        event.setName(eventDetails.getName());
        event.setStartDate(eventDetails.getStartDate());
        event.setEndDate(eventDetails.getEndDate());
        event.setLocation(eventDetails.getLocation());
        event.setMaxParticipants(eventDetails.getMaxParticipants());

        return eventRepository.save(event);
    }

    // Ta bort event
    public void delete(Long id) {
        Event event = findById(id);
        eventRepository.delete(event);
    }

    // Hitta kommande events
    public List<Event> findUpcomingEvents() {
        return eventRepository.findByStartDateAfter(LocalDate.now());
    }

    // Sök events på namn
    public List<Event> searchByName(String name) {
        return eventRepository.findByNameContainingIgnoreCase(name);
    }
}