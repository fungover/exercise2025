package org.example.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    @Column(nullable = false, length = 20)
    private String name;

    @NotBlank(message = "Species cannot be blank")
    @Size(min = 1, max = 20, message = "Species must be between 1 and 20 characters")
    @Column(nullable = false, length = 20)
    private String species;

    @Min(value = 0, message = "Hunger level must be between 0 and 10")
    @Max(value = 10, message = "Hunger level must be between 0 and 10")
    @Column(nullable = false)
    private int hungerLevel = 5;

    @Min(value = 0, message = "Happiness level must be between 0 and 10")
    @Max(value = 10, message = "Happiness level must be between 0 and 10")
    @Column(nullable = false)
    private int happinessLevel = 5;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Default constructor
    public Pet() {
    }

    // Constructor for creating new pets
    public Pet(String name, String species) {
        this.name = name;
        this.species = species;
    }

    // Full constructor
    public Pet(Long id, String name, String species, int hungerLevel, int happinessLevel) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.hungerLevel = hungerLevel;
        this.happinessLevel = happinessLevel;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public int getHappinessLevel() {
        return happinessLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public void setHappinessLevel(int happinessLevel) {
        this.happinessLevel = happinessLevel;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", hungerLevel=" + hungerLevel +
                ", happinessLevel=" + happinessLevel +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Pet pet = (Pet) o;
        return getId() != null && Objects.equals(getId(), pet.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
