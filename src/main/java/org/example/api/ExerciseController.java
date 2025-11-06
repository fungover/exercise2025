package org.example.api;

import org.example.api.dto.ExerciseView;
import org.example.repo.ExerciseRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    private final ExerciseRepository repo;

    public ExerciseController(ExerciseRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<ExerciseView> list() {
        return repo.findAll().stream().map(e -> new ExerciseView(e.getId(), e.getName(), e.getMuscleGroup())).toList();
    }
}

