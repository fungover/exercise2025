package exercise8.repository;

import exercise8.entity.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long> {

    // Find allergen by name
    Optional<Allergen> findByName(String name);
}
