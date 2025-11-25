package org.example;

public record CreateMovieDTO (
    String title,
    String genre,
    String description,
    int year,
    int runtimeMinutes
) {}
