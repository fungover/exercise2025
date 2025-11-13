package org.example.controllers;

import org.example.MovieDTO;
import org.example.repos.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("api")
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
}
