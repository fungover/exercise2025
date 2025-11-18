package org.example.controller;

import org.example.service.workout.WorkoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
