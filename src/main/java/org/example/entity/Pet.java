package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Pet {

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return age == pet.age &&
                Objects.equals(id, pet.id) &&
                Objects.equals(species, pet.species) &&
                Objects.equals(name, pet.name) &&
                Objects.equals(birthDate, pet.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, species, name, age);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String species;
    private String name;
    private Integer age;
    private String birthDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Pet() {
    }

    public Pet(String species, String name, int age, String birthDate) {
        this.species = species;
        this.name = name;
        this.age = age;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthDate() {return birthDate;}

    public void setBirthDate(String birthDate) {this.birthDate = birthDate;}

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
