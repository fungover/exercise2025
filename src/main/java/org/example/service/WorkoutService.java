package org.example.service;

import org.example.model.Workout;

import java.util.List;

public interface WorkoutService {
    List<Workout> getAllWorkouts();
    Workout createWorkout(Workout workout);
    Workout getWorkoutById(Long id);
}
