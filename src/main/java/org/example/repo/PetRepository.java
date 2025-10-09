package org.example.repo;

import org.example.domain.Pet;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public interface PetRepository {
    Pet add(Pet newPetWithoutId);

    List<Pet> findAll();

    Optional<Pet> findById(long id);

    Pet update(long id, UnaryOperator<Pet> change);

    boolean delete(long id);
}
