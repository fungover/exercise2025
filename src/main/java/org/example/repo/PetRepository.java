package org.example.repo;

import org.example.domain.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Pet add(Pet newPetWithoutId);

    List<Pet> findAll();

    Optional<Pet> findById(long id);
}
