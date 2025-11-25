package org.example.controllers;

import org.example.CreateMovieDTO;
import org.example.MovieDTO;
import org.example.entities.Movie;
import org.example.repos.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public List<MovieDTO> getAll() {
        return movieRepository.findAll().stream()
                .map(movie -> new MovieDTO(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getGenre(),
                        movie.getDescription(),
                        movie.getYear(),
                        movie.getRuntimeMinutes()))
                .toList();
    }

    @PostMapping("/movies")
    @PreAuthorize("hasRole('USER')")
    public String createMovie(@RequestBody CreateMovieDTO dto) {
        Movie movie = new Movie(
                dto.title(),
                dto.genre(),
                dto.description(),
                dto.year(),
                dto.runtimeMinutes()
        );

        movieRepository.save(movie);

        return "Movie created successfully";

    }

    @DeleteMapping("/movies/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMovie(@PathVariable Integer id) {

        if (!movieRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
        movieRepository.deleteById(id);
    }
}
