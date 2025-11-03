package org.example.dto.request.movie;

import jakarta.validation.constraints.NotNull;

public class CreateMovieRequest {
    @NotNull
    private String title;

    @NotNull
    private Long duration;

    @NotNull
    private String genre;

    private Long directorId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Long directorId) {
        this.directorId = directorId;
    }
}
