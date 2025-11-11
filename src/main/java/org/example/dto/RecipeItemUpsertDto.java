package org.example.dto;

public record RecipeItemUpsertDto(
        String name,
        double amount,
        String unit
) {
}
