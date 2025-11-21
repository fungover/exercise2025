package org.example;

import org.example.entities.Pet;
import org.springframework.data.repository.ListCrudRepository;

public interface PetRepository extends ListCrudRepository<Pet, Integer> {
    void removeById(Integer id);
}
