package org.example.repositories;

import org.example.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    // Find pets by species, ignoring case - Spring Data JPA query method
    List<Pet> findBySpeciesIgnoreCase(String species);

    // Find pet by name, ignoring case - Optional for future use if needed
    Optional<Pet> findByNameIgnoreCase(String name);
}
