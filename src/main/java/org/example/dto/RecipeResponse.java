package org.example.dto;

import java.util.List;

public record RecipeResponse (
    Integer id,
    String title,
    String instructions,
    List<RecipeItemResponse> items
) {}
