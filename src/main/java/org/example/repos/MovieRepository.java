package org.example.repos;

import org.example.entities.Movie;
import org.springframework.data.repository.ListCrudRepository;

public interface MovieRepository extends ListCrudRepository<Movie, Integer> {
}
