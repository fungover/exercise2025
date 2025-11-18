package org.example.service.workout;

import org.example.model.Workout;

import java.util.List;

// detta ska service KUNNA göra
public interface WorkoutService {
    List<Workout> getAllWorkouts();
    Workout createWorkout(Workout workout);
    Workout getWorkoutById(Long id);
}
