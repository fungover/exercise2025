package org.example.repository;

import org.example.model.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface RecipeRepository extends ListCrudRepository<Recipe, Integer> {

    @EntityGraph(attributePaths = "items")
    @NonNull
    Optional<Recipe> findById(@NonNull Integer id);
}
