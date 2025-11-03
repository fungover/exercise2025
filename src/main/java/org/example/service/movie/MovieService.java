package org.example.service.movie;

import org.example.entities.Movie;

import java.util.List;

public interface MovieService {
    Movie addMovie(Long directorId, Movie movie);
    List<Movie> getAllMovies();
    Movie getMovie(Long id);
    Movie updateMovie(Long id, Movie movie);
    void deleteMovie(Long id);
}
