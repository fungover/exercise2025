package org.example.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.Workout;
import org.example.service.workout.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WorkoutController.class)
class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkoutService workoutService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public WorkoutService workoutService() {
            return Mockito.mock(WorkoutService.class);
        }
    }

    @BeforeEach
    void setup() {

        List<Workout> workouts = Arrays.asList(
                new Workout("Run", 30, 200, LocalDate.now()),
                new Workout("Bike", 45, 400, LocalDate.now())
        );
        Mockito.when(workoutService.getAllWorkouts()).thenReturn(workouts);
        Mockito.when(workoutService.getWorkoutById(1L))
                .thenReturn(new Workout("Run", 30, 200, LocalDate.now()));
        Mockito.when(workoutService.createWorkout(Mockito.any())).thenAnswer(i -> i.getArgument(0));
        Mockito.doNothing().when(workoutService).deleteWorkout(Mockito.anyLong());
    }

    @Test
    void testGetAllWorkouts_authenticated() throws Exception {
        mockMvc.perform(get("/api/workouts").with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllWorkouts_unauthenticated() throws Exception {
        mockMvc.perform(get("/api/workouts"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testDeleteWorkout_authenticated() throws Exception {
        mockMvc.perform(delete("/api/workouts/1")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateWorkout_authenticated() throws Exception {

        Workout workout = new Workout("Swim", 60, 600, LocalDate.now());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String workoutJson = objectMapper.writeValueAsString(workout);

        mockMvc.perform(post("/api/workouts")
                        .with(user("admin").roles("ADMIN"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workoutJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Swim"))
                .andExpect(jsonPath("$.duration").value(60))
                .andExpect(jsonPath("$.caloriesBurned").value(600));
    }
}
