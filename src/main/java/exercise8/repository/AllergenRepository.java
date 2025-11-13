package exercise8.repository;

import exercise8.entity.Allergen;
import exercise8.entity.AllergenSeverity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long> {

    // Hitta allergen via namn
    Optional<Allergen> findByName(String name);

    // Hitta alla allergener med viss severity
    List<Allergen> findBySeverity(AllergenSeverity severity);

    // Hitta kritiska allergener
    List<Allergen> findBySeverityOrderByNameAsc(AllergenSeverity severity);
}
