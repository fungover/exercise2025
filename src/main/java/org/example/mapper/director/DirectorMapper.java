package org.example.mapper.director;

import org.example.dto.request.director.CreateDirectorRequest;
import org.example.dto.response.director.DirectorResponse;
import org.example.entities.Director;
import org.example.entities.Movie;
import org.example.mapper.movie.MovieMapper;

import java.util.List;

public class DirectorMapper {

    public static Director toEntity(CreateDirectorRequest request) {
        Director director = new Director();
        director.setFirstName(request.getFirstName());
        director.setLastName(request.getLastName());

        if (request.getMovies() != null && !request.getMovies().isEmpty()) {
            List<Movie> movies = request.getMovies().stream()
                    .map(MovieMapper::toEntity)
                    .peek(movie -> movie.setDirector(director))
                    .toList();
            director.setMovies(movies);
        }

        return director;
    }

    public static DirectorResponse toResponse(Director director) {
        DirectorResponse response = new DirectorResponse();
        response.setId(director.getId());
        response.setFirstName(director.getFirstName());
        response.setLastName(director.getLastName());

        if (director.getMovies() != null && !director.getMovies().isEmpty()) {
            response.setMovies(
                    director.getMovies().stream()
                            .map(MovieMapper::mapToResponse)
                            .toList()
            );
        }

        return response;
    }
}
