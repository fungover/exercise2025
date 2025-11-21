package org.example.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pet {
    @Id
    private Integer id;

    String name;

    int age;

    String species;

    int hungerLevel;

    int happiness;
}