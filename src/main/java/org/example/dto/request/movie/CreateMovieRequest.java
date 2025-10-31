package org.example.dto.request.movie;

import jakarta.validation.constraints.NotNull;

public class CreateMovieRequest {
    @NotNull
    private String title;

    @NotNull
    private Long duration;

    @NotNull
    private String genre;

    @NotNull
    private Long directorId;
}
