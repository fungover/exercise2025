package org.example.api.movie;

import jakarta.validation.Valid;
import org.example.dto.request.movie.CreateMovieRequest;
import org.example.dto.request.movie.UpdateMovieRequest;
import org.example.dto.response.movie.MovieResponse;
import org.example.entities.Movie;
import org.example.mapper.movie.MovieMapper;
import org.example.service.movie.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(@Valid @RequestBody CreateMovieRequest request) {

        Movie movie = MovieMapper.toEntity(request);
        Movie savedMovie = movieService.addMovie(request.getDirectorId(), movie);
        MovieResponse response = MovieMapper.mapToResponse(savedMovie);

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<MovieResponse> responses = movieService.getAllMovies().stream()
                .map(MovieMapper::mapToResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovie(@PathVariable Long id) {
        Movie movie = movieService.getMovie(id);
        MovieResponse response = MovieMapper.mapToResponse(movie);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMovieRequest request) {

        Movie movie = MovieMapper.toEntity(id, request);
        Movie updatedMovie = movieService.updateMovie(id, movie);
        MovieResponse response = MovieMapper.mapToResponse(updatedMovie);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);

        return ResponseEntity.noContent().build();
    }
}
