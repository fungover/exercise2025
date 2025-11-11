package org.example.dto;

import java.util.List;

public record RecipeUpsertDto(
        String title,
        String instructions,
        List<RecipeItemUpsertDto> items
) {
}
