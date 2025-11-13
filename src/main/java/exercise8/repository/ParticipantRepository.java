package exercise8.repository;

import exercise8.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    // Hitta deltagare via email
    Optional<Participant> findByEmail(String email);

    // Hitta alla deltagare i en scoutkår
    List<Participant> findByPatrolId(Long patrolId);

    // Hitta deltagare med en specifik allergi
    @Query("SELECT p FROM Participant p JOIN p.allergens a WHERE a.id = :allergenId")
    List<Participant> findByAllergenId(@Param("allergenId") Long allergenId);

    // Sök deltagare på namn
    @Query("SELECT p FROM Participant p WHERE " +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Participant> searchByName(@Param("searchTerm") String searchTerm);
}

