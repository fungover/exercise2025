package org.example.service.movie;

import org.example.entities.Director;
import org.example.entities.Movie;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.movie.MovieRepository;
import org.example.service.director.DirectorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    MovieRepository movieRepository;
    DirectorService directorService;

    public MovieServiceImpl(MovieRepository movieRepository, DirectorService directorService) {
        this.movieRepository = movieRepository;
        this.directorService = directorService;
    }

    @Transactional
    @Override
    public Movie addMovie(Long directorId, Movie movie) {
        Director director = directorService.getDirector(directorId);
        movie.setDirector(director);

        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovie(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    @Transactional
    @Override
    public Movie updateMovie(Long id, Movie movie) {
        Movie existingMovie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));

        if (movie.getTitle() != null) {
            existingMovie.setTitle(movie.getTitle());
        }

        if (movie.getDuration() != null) {
            existingMovie.setDuration(movie.getDuration());
        }

        if (movie.getGenre() != null) {
            existingMovie.setGenre(movie.getGenre());
        }

        return movieRepository.save(existingMovie);
    }

    @Transactional
    @Override
    public void deleteMovie(Long id) {
        movieRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));

        movieRepository.deleteById(id);
    }
}
