package exercise8.repository;

import exercise8.entity.Patrol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatrolRepository extends JpaRepository<Patrol, Long> {

    // Find scout troop by name
    Optional<Patrol> findByName(String name);

    // Find all scout troops in a district
    List<Patrol> findByDistrict(String district);

    // Search scout troops by name
    List<Patrol> findByNameContainingIgnoreCase(String name);
}