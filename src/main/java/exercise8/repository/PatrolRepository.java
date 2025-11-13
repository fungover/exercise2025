package exercise8.repository;

import exercise8.entity.Patrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatrolRepository extends JpaRepository<Patrol, Long> {

    // Hitta scoutkår via namn
    Optional<Patrol> findByName(String name);

    // Hitta alla scoutkårer i ett distrikt
    List<Patrol> findByDistrict(String district);

    // Sök scoutkårer på namn
    List<Patrol> findByNameContainingIgnoreCase(String name);
}