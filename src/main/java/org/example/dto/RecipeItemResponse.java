package org.example.dto;


public record RecipeItemResponse(
        Integer id,
        String name,
        double amount,
        String unit
) {}
