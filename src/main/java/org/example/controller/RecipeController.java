package org.example.controller;

import org.example.dto.RecipeListResponse;
import org.example.dto.RecipeResponse;
import org.example.mapper.RecipeMapper;
import org.example.model.Recipe;
import org.example.repository.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {

    RecipeRepository repository;

    public RecipeController(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/recipes")
    @Transactional(readOnly = true)
    public List<RecipeListResponse> listRecipes() {
        return repository.findAll().stream()
                .map(RecipeMapper::toList)
                .toList();
    }

    @GetMapping("/recipes/{id}")
    @Transactional(readOnly = true)
    public RecipeResponse getRecipe(@PathVariable Integer id) {
        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));
        return RecipeMapper.toDetail(recipe);
    }
}
