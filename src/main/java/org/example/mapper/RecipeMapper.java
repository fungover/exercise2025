package org.example.mapper;

import org.example.dto.*;
import org.example.model.Recipe;
import org.example.model.RecipeItem;

import java.util.List;

public final class RecipeMapper {
    private RecipeMapper() {
    }

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

    public static Recipe fromUpsert(RecipeUpsertDto dto) {
        Recipe.Builder recipeBuilder = Recipe.builder()
                .title(dto.title())
                .instructions(dto.instructions());

        if (dto.items() != null) {
            for (RecipeItemUpsertDto item : dto.items()) {
                recipeBuilder.addItem(RecipeItem.builder()
                        .name(item.name())
                        .amount(item.amount())
                        .unit(item.unit())
                        .build());
            }
        }
        return recipeBuilder.build();
    }

    public static void apply(Recipe target, RecipeUpsertDto dto) {
        List<RecipeItem> rebuilt =
                dto.items() == null ? List.of()
                        : dto.items().stream()
                        .map(item -> RecipeItem.builder()
                                .name(item.name())
                                .amount(item.amount())
                                .unit(item.unit())
                                .build())
                        .toList();

        target.replaceContent(dto.title(), dto.instructions(), rebuilt);
    }
}
