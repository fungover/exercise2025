package org.example.entities;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDate;

@Entity
@Table (name="catches")

public class Catch {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="catch_id")
    private Long id;

    @Column(name="species", nullable=false)
    private String species;

    @Column(name="weight_g", nullable=false)
    private Double weight;

    @Column(name="length", nullable=false)
    private Double length;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    LocalDate createdAt;

    public Catch() {}

    public Catch(String species, double weight, double length) {
        this.species = species;
        this.weight = weight;
        this.length = length;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public String getSpecies() {
        return species;
    }

    public Double getWeight() {
        return weight;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
