package org.example.controller;

import org.example.dto.RecipeItemResponse;
import org.example.dto.RecipeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @GetMapping("/recipes")
    public List<RecipeResponse> listRecipes() {
        return List.of(new RecipeResponse(232,"Tacos", "Use a good knife",
                List.of(
                        new RecipeItemResponse(1L, "Meat", 500.0, "g"),
                        new RecipeItemResponse(2L, "Tortillas", 8.0, "pcs"),
                        new RecipeItemResponse(3L, "Onion", 1.0, "pcs")
        )));
    }
}
