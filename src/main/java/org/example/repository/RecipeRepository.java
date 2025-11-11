package org.example.repository;

import org.example.model.Recipe;
import org.springframework.data.repository.ListCrudRepository;

public interface RecipeRepository extends ListCrudRepository<Recipe, Integer> {}
