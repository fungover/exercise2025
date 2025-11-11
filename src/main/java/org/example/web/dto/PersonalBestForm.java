package org.example.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PersonalBestForm {
    @NotNull
    private Long exerciseId;

    @NotNull
    @Min(1)
    private Integer reps;

    @NotNull
    private BigDecimal weightKg;

    @NotNull
    private LocalDate achievedOn;

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public LocalDate getAchievedOn() {
        return achievedOn;
    }

    public void setAchievedOn(LocalDate achievedOn) {
        this.achievedOn = achievedOn;
    }
}
