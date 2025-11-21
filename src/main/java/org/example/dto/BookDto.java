package org.example.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
        @NotNull
        @Min(value = 0, message = "Rating must be at least 0")
        @Max(value = 10, message = "Rating must not exceed 10")
        int rating,
        @NotNull
        Author author,
        @NotNull
        Language language) {
}
