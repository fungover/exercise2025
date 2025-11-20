package org.example;

import org.example.enteties.Pet;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface PetRepository extends ListCrudRepository<Pet, Integer> {
    Optional<Pet> findPetById(Integer id);
    //void savePet(Pet pet);
    void deletePetById(Integer id);

}
