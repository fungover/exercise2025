package org.example.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.hibernate.annotations.CreationTimestamp;
import java.time.OffsetDateTime;

@Entity
@Table (name="catches")
public class Catch {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="catch_id")
    private Long id;

    @NotNull(message = "Species cannot be null")
    @Column(name="species", nullable=false)
    private String species;

    @Positive(message = "Weight must be positive")
    @DecimalMin("0.0")
    @DecimalMax("50000.0")
    @Column(name="weight_g", nullable=false)
    private Double weight;

    @Positive(message = "Length must be positive")
    @Column(name="length", nullable=false)
    @DecimalMin("0.0")
    @DecimalMax("500.0")
    private Double length;

    @CreationTimestamp
    @Column(
            name = "caught_at",
            updatable = false,
            nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private OffsetDateTime caughtAt;

    public Catch() {}

    public Catch(String species, double weight, double length) {
        this.species = species;
        this.weight = weight;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Double getWeight() {
        return weight;
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

    public OffsetDateTime getCaughtAt() {
        return caughtAt;
    }

    public void setCaughtAt(OffsetDateTime caughtAt) {
        this.caughtAt = caughtAt;
    }

}
