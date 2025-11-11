package org.example.controller;

import org.example.dto.RecipeListResponse;
import org.example.dto.RecipeResponse;
import org.example.service.RecipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {
    private final RecipeService service;

    public RecipeController(RecipeService service) {
        this.service = service;
    }

    @GetMapping("/recipes")
    public List<RecipeListResponse> listRecipes() {
        return service.listAll();
    }

    @GetMapping("/recipes/{id}")
    public RecipeResponse getRecipe(@PathVariable Integer id) {
        return service.getById(id);
    }
}
