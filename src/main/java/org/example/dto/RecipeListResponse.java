package org.example.dto;

public record RecipeListResponse(
        Integer id,
        String title,
        String instructions
) {}
