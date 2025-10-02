package org.example.repository;

import org.example.dto.PetDTO;

import java.util.Collection;
import java.util.Optional;

// Repository interface for Pet data operations
public interface PetRepository {

    PetDTO save(PetDTO pet); // Save a new pet

    Collection<PetDTO> findAll(); // Retrieve all pets

    Optional<PetDTO> findById(Long id); // Find a pet by its ID

    boolean delete(Long id); // Delete a pet by its ID

    PetDTO update(PetDTO pet); // Update an existing pet
}
