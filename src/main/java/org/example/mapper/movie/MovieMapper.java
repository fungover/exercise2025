package org.example.mapper.movie;

import org.example.dto.response.movie.MovieResponse;
import org.example.entities.Movie;

public class MovieMapper {
    public static MovieResponse mapToResponse(Movie movie) {
        MovieResponse response = new MovieResponse();
        response.setId(movie.getId());
        response.setTitle(movie.getTitle());
        response.setDuration(movie.getDuration());
        response.setGenre(movie.getGenre());
        response.setDirectorId(movie.getDirector().getId());

        return response;
    }
}
