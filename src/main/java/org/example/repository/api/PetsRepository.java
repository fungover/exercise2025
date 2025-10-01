package org.example.repository.api;

import org.example.dto.PetDTO;

import java.util.List;
import java.util.Optional;

public interface PetsRepository {
    PetDTO save(PetDTO pet);
    List<PetDTO> findAll();
    Optional<PetDTO> findById(Long id);
    void deleteById(Long id);
    Long generateId();
}