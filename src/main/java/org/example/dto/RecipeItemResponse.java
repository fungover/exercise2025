package org.example.dto;


public record RecipeItemResponse(
        Long id,
        String name,
        double amount,
        String unit
) {}
