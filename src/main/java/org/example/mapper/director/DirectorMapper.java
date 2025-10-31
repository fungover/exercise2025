package org.example.mapper.director;

import org.example.dto.response.director.DirectorResponse;
import org.example.entities.Director;
import org.example.mapper.movie.MovieMapper;

public class DirectorMapper {
    public static DirectorResponse mapToResponse(Director director) {
        DirectorResponse response = new DirectorResponse();
        response.setId(director.getId());
        response.setFirstName(director.getFirstName());
        response.setLastName(director.getLastName());

        if (director.getMovies() != null) {
            response.setMovies(
                    director.getMovies().stream()
                            .map(MovieMapper::mapToResponse)
                            .toList()
            );
        }

        return response;
    }
}
