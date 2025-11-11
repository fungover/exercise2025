package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.RecipeListResponse;
import org.example.dto.RecipeResponse;
import org.example.dto.RecipeUpsertDto;
import org.example.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping("/recipes")
    public ResponseEntity<RecipeResponse> create(@Valid @RequestBody RecipeUpsertDto dto) {
        RecipeResponse created = service.create(dto);
        return ResponseEntity
                .created(URI.create("/api/recipes/" + created.id()))
                .body(created);
    }

    @PutMapping("/recipes/{id}")
    public RecipeResponse update(@PathVariable Integer id, @Valid @RequestBody RecipeUpsertDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/recipes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
