package org.example.dto.response.director;

import org.example.dto.response.movie.MovieResponse;
import org.example.entities.Movie;

import java.util.List;

public class DirectorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private List<MovieResponse> movies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<MovieResponse> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieResponse> movies) {
        this.movies = movies;
    }
}
