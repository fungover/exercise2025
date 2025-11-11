package org.example.mapper;

import org.example.dto.*;
import org.example.model.*;

import java.util.List;

public final class RecipeMapper {
    private RecipeMapper() {}

    public static RecipeListResponse toList(Recipe recipe) {
        return new RecipeListResponse(recipe.getId(), recipe.getTitle(), recipe.getInstructions());
    }

    public static RecipeResponse toDetail(Recipe recipe) {
        List<RecipeItemResponse> items = recipe.getItems().stream()
                .map(RecipeMapper::toItem)
                .toList();
        return new RecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getInstructions(), items);
    }

    private static RecipeItemResponse toItem(RecipeItem item) {
        return new RecipeItemResponse(item.getId(), item.getName(), item.getAmount(), item.getUnit());
    }
}
