package exercise8.service;

import exercise8.entity.*;
import exercise8.exception.RegistrationFullException;
import exercise8.exception.ResourceNotFoundException;
import exercise8.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private ParticipantService participantService;

    // Hämta alla registreringar
    public List<Registration> findAll() {
        return registrationRepository.findAll();
    }

    // Hämta registrering via ID
    public Registration findById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration", "id", id));
    }

    // Skapa ny registrering
    public Registration create(Registration registration) {
        Event event = registration.getEvent();

        // Kolla om deltagaren redan är registrerad
        if (registrationRepository.existsByEventIdAndParticipantId(
                event.getId(), registration.getParticipant().getId())) {
            throw new IllegalStateException("Participant is already registered for this event");
        }

        // Kolla om eventet har plats
        if (event.getMaxParticipants() != null) {
            long confirmedCount = registrationRepository.countConfirmedByEventId(event.getId());
            if (confirmedCount >= event.getMaxParticipants()) {
                throw new RegistrationFullException(event.getName(), event.getMaxParticipants());
            }
        }

        registration.setRegistrationDate(LocalDateTime.now());
        registration.setStatus(RegistrationStatus.PENDING);

        return registrationRepository.save(registration);
    }

    // Uppdatera registrering
    public Registration update(Long id, Registration registrationDetails) {
        Registration registration = findById(id);

        registration.setStatus(registrationDetails.getStatus());
        registration.setSpecialRequests(registrationDetails.getSpecialRequests());

        return registrationRepository.save(registration);
    }

    // Bekräfta registrering
    public Registration confirm(Long id) {
        Registration registration = findById(id);
        registration.setStatus(RegistrationStatus.CONFIRMED);
        return registrationRepository.save(registration);
    }

    // Avboka registrering
    public Registration cancel(Long id) {
        Registration registration = findById(id);
        registration.setStatus(RegistrationStatus.CANCELLED);
        return registrationRepository.save(registration);
    }

    // Ta bort registrering
    public void delete(Long id) {
        Registration registration = findById(id);
        registrationRepository.delete(registration);
    }

    // Hitta alla registreringar för ett event
    public List<Registration> findByEventId(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    // Hitta alla registreringar för en deltagare
    public List<Registration> findByParticipantId(Long participantId) {
        return registrationRepository.findByParticipantId(participantId);
    }

    // Hitta bekräftade registreringar för ett event
    public List<Registration> findConfirmedByEventId(Long eventId) {
        return registrationRepository.findByEventIdAndStatus(eventId, RegistrationStatus.CONFIRMED);
    }
}
