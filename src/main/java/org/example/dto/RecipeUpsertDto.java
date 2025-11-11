package org.example.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record RecipeUpsertDto(
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must be at most 100 characters")
        String title,

        @NotBlank(message = "Instructions are required")
        String instructions,

        @Valid
        List<RecipeItemUpsertDto> items
) {
}
