package org.example.controller;

import org.example.model.Workout;
import org.example.service.workout.WorkoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/workouts")
public class WorkoutViewController {

    private final WorkoutService workoutService;

    public WorkoutViewController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping
    public String getWorkoutsPage(Model model) {
        model.addAttribute("workouts", workoutService.getAllWorkouts());
        return "workouts";
    }

    @PostMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return "redirect:/workouts";
    }

    @PostMapping("/add")
    public String addWorkout(@ModelAttribute Workout workout) {
        workoutService.createWorkout(workout);
        return "redirect:/workouts";
    }
}
