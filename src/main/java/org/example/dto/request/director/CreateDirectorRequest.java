package org.example.dto.request.director;

import jakarta.validation.constraints.NotNull;
import org.example.dto.request.movie.CreateMovieRequest;
import org.example.entities.Movie;

import java.util.List;

public class CreateDirectorRequest {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private List<CreateMovieRequest> movies;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<CreateMovieRequest> getMovies() {
        return movies;
    }

    public void setMovies(List<CreateMovieRequest> movies) {
        this.movies = movies;
    }
}
