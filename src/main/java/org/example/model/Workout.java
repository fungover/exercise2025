package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDate date;

    // måste finnas en tom konstruktor
    public Workout() {
    }

    // konstruktor med alla fält förutom Id eftersom Id ska autogenereras
    public Workout(String name, Integer duration, Integer caloriesBurned, LocalDate date) {
        this.name = name;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.date = date;
    }
}
