package org.example.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "personal_bests",
        uniqueConstraints = @UniqueConstraint(name = "uq_pr_user_ex_reps", columnNames = {"user_id", "exercise_id", "reps"}))
public class PersonalBestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id")
    private ExerciseEntity exercise;
    @Column(name = "weight_kg", nullable = false, precision = 6, scale = 2)
    private BigDecimal weightKg;
    @Column(nullable = false)
    private int reps;
    @Column(name = "achieved_on", nullable = false)
    private LocalDate achievedOn;

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity u) {
        this.user = u;
    }

    public ExerciseEntity getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseEntity e) {
        this.exercise = e;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal w) {
        this.weightKg = w;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int r) {
        this.reps = r;
    }

    public LocalDate getAchievedOn() {
        return achievedOn;
    }

    public void setAchievedOn(LocalDate a) {
        this.achievedOn = a;
    }
}

