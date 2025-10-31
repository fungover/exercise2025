package org.example.service.director;

import org.example.dto.request.director.CreateDirectorRequest;
import org.example.dto.request.director.UpdateDirectorRequest;
import org.example.dto.request.movie.CreateMovieRequest;
import org.example.dto.response.director.DirectorResponse;
import org.example.entities.Director;
import org.example.entities.Movie;
import org.example.repository.director.DirectorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.mapper.director.DirectorMapper.mapToResponse;

@Service
public class DirectorServiceImpl implements DirectorService {
    private final DirectorRepository directorRepository;

    public DirectorServiceImpl(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @Override
    public DirectorResponse addDirector(CreateDirectorRequest request) {
        Director director = new Director();
        director.setFirstName(request.getFirstName());
        director.setLastName(request.getLastName());

        if (request.getMovies() != null && !request.getMovies().isEmpty()) {
            List<Movie> movies = new ArrayList<>();
            for (CreateMovieRequest m : request.getMovies()) {
                Movie movie = new Movie();
                movie.setTitle(m.getTitle());
                movie.setDuration(m.getDuration());
                movie.setDirector(director);
                movies.add(movie);
            }
            director.setMovies(movies);
        }

        Director savedDirector = directorRepository.save(director);

        return mapToResponse(savedDirector);
    }

    @Override
    public DirectorResponse updateDirector(UpdateDirectorRequest director) {
        return null;
    }

    @Override
    public DirectorResponse getDirector(Long id) {
        return null;
    }

    @Override
    public void deleteDirector(Long id) {
    }
}
