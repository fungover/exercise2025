package org.example.repository;

import org.example.entities.Author;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends ListCrudRepository<Author,Integer> {

    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

}
