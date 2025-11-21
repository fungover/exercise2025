package exercise8.repository;

import exercise8.entity.Registration;
import exercise8.entity.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // Find all registrations for an event
    List<Registration> findByEventId(Long eventId);

    // Find all registrations for a participant
    List<Registration> findByParticipantId(Long participantId);

    // Find registrations with a certain status
    List<Registration> findByStatus(RegistrationStatus status);

    // Find registrations for an event with a specific status
    List<Registration> findByEventIdAndStatus(Long eventId, RegistrationStatus status);

    // Check if a participant is already registered for an event
    boolean existsByEventIdAndParticipantId(Long eventId, Long participantId);

    // Count the number of confirmed registrations for an event
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.event.id = :eventId AND r.status = 'CONFIRMED'")
    long countConfirmedByEventId(@Param("eventId") Long eventId);

    // Find specific registration
    Optional<Registration> findByEventIdAndParticipantId(Long eventId, Long participantId);
}
