package org.example;

import org.example.entities.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends ListCrudRepository<Pet, Integer> {
    Optional<Pet> findPetByName(String name);

    // JPQL-query
    @Query("""
    SELECT pet.id, upper(pet.name), pet.type, pet.createdAt FROM Pet pet WHERE pet.name = :name
    """)
    Optional<Pet> findByName(@Param("name") String name);

    @Query("""
        from Pet pet join fetch pet.favorite f 
        """)
    List<Pet> findPetsAndFood();

    @EntityGraph("Pet.favorite")
    List<Pet> findPetsBy();

}
