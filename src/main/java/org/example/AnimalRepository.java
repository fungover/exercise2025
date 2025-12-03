package org.example;

import org.example.entity.Pet;
import org.springframework.data.repository.ListCrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AnimalRepository extends ListCrudRepository<Pet, Integer> {
    // Optional search methods for sql queries
    Optional<Pet> findByName(String name);
    Optional<Pet> findBySpecies(String species);
    Optional<Pet> findByAge(int age);
    Optional<Pet> findById(int id);
    Optional<Pet> findByBirthDate(String birthDate);
    Optional<Pet> findByCreatedAt(LocalDateTime createdAt);
}
