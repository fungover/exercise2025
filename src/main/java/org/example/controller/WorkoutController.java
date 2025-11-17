package org.example.controller;


import org.example.model.Workout;
import org.example.repository.WorkoutRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutRepository workoutRepository;

    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    // Hämta alla workouts
    @GetMapping
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    // skapa ny workout
    @PostMapping
    public Workout createWorkout(@RequestBody Workout workout) {
        return workoutRepository.save(workout);
    }
}
