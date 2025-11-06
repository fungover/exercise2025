package org.example;

import org.example.entities.Author;
import org.springframework.data.repository.ListCrudRepository;

public interface AuthorRepository extends ListCrudRepository<Author,Integer> {
}
