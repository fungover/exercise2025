package org.example.controller;

import org.example.dto.RecipeItemResponse;
import org.example.dto.RecipeResponse;
import org.example.model.Recipe;
import org.example.repository.RecipeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {

    RecipeRepository repository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.repository = recipeRepository;
    }

    @GetMapping("/recipes")
    @Transactional(readOnly = true)
    public List<RecipeResponse> listRecipes() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private RecipeResponse toResponse(Recipe recipe) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getInstructions(),
                recipe.getItems().stream()
                        .map(item -> new RecipeItemResponse(
                                item.getId(),
                                item.getName(),
                                item.getAmount(),
                                item.getUnit()
                        ))
                        .toList()
        );
    }
}
