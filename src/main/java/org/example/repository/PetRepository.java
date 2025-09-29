package org.example.repository;

import org.example.dto.PetDTO;

import java.util.Collection;
import java.util.Optional;

public interface PetRepository {

    PetDTO save(PetDTO pet);

    Collection<PetDTO> findAll();

    Optional<PetDTO> findById(Long id);

    boolean delete(Long id);
    
    PetDTO update(PetDTO pet);
}
