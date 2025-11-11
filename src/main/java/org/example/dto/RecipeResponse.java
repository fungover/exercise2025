package org.example.dto;

import java.util.List;

public record RecipeResponse (
    long id,
    String title,
    String instructions,
    List<RecipeItemResponse> items
) {}
