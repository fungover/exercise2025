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

    // Hitta alla registreringar för ett event
    List<Registration> findByEventId(Long eventId);

    // Hitta alla registreringar för en deltagare
    List<Registration> findByParticipantId(Long participantId);

    // Hitta registreringar med viss status
    List<Registration> findByStatus(RegistrationStatus status);

    // Hitta registreringar för ett event med viss status
    List<Registration> findByEventIdAndStatus(Long eventId, RegistrationStatus status);

    // Kolla om en deltagare redan är registrerad till ett event
    boolean existsByEventIdAndParticipantId(Long eventId, Long participantId);

    // Räkna antal bekräftade registreringar för ett event
    @Query("SELECT COUNT(r) FROM Registration r WHERE r.event.id = :eventId AND r.status = 'CONFIRMED'")
    long countConfirmedByEventId(@Param("eventId") Long eventId);

    // Hitta specifik registrering
    Optional<Registration> findByEventIdAndParticipantId(Long eventId, Long participantId);
}
