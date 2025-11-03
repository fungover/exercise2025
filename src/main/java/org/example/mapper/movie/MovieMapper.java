package org.example.mapper.movie;

import org.example.dto.request.movie.CreateMovieRequest;
import org.example.dto.request.movie.UpdateMovieRequest;
import org.example.dto.response.movie.MovieResponse;
import org.example.entities.Movie;

public class MovieMapper {

    public static Movie toEntity(CreateMovieRequest request) {
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDuration(request.getDuration());
        movie.setGenre(request.getGenre());

        return movie;
    }

    public static Movie toEntity(Long id, UpdateMovieRequest request) {
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setDuration(request.getDuration());

        return movie;
    }

    public static MovieResponse mapToResponse(Movie movie) {
        MovieResponse response = new MovieResponse();
        response.setId(movie.getId());
        response.setTitle(movie.getTitle());
        response.setDuration(movie.getDuration());
        response.setGenre(movie.getGenre());
        // Director is guaranteed to exist due to @JoinColumn(nullable = false)
        response.setDirectorId(movie.getDirector().getId());

        return response;
    }
}
