package org.example;

import org.example.entities.Pet;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface PetRepository extends ListCrudRepository<Pet, Integer> {
    Optional<Pet> findPetByName(String name);

}
