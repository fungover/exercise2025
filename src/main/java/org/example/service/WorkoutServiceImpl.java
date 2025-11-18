package org.example.service;

import org.example.model.Workout;
import org.example.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Här skriver jag hur metoderna faktiskt FUNGERAR
@Service
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    // Override = "detta är en metod som deklarerats i interfacet workoutService"
    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public Workout createWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Override
    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id).orElse(null);
    }
}
