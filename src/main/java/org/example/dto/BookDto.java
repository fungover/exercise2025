package org.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.Genre;
import org.example.entities.Author;
import org.example.entities.Language;

public record BookDto(

        @NotEmpty(message = "Name is mandatory")
        String title,
        @NotNull
        Genre genre,
        int Rating,
        @NotNull
        Author author,
        @NotNull
        Language language) {
}
