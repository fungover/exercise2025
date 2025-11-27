package org.example;

import org.example.entity.Pet;
import org.springframework.data.repository.ListCrudRepository;

public interface AnimalRepository extends ListCrudRepository<Pet, Integer> {

}
