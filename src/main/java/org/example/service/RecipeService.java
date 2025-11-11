package org.example.service;

import org.example.dto.RecipeListResponse;
import org.example.dto.RecipeResponse;
import org.example.mapper.RecipeMapper;
import org.example.model.Recipe;
import org.example.repository.RecipeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<RecipeListResponse> listAll() {
        return repository.findAll().stream()
                .map(RecipeMapper::toList)
                .toList();
    }

    @Transactional(readOnly = true)
    public RecipeResponse getById(Integer id) {
        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));
        return RecipeMapper.toDetail(recipe);
    }
}
