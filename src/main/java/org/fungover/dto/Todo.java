package org.fungover.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record Todo(
        Long id,
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 100, message = "Title must be less than 100 characters")
        String title,
        @Size(max = 500, message = "Description must be less than 500 characters")
        String description,
        boolean completed,
        @NotNull(message = "Due date is required")
        @FutureOrPresent(message = "Due date must be today or in the future")
        LocalDate dueDate
) {
}
