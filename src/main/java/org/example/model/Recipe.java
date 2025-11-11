package org.example.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String instructions;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeItem> items = new ArrayList<>();

    protected Recipe() {}

    // getters
    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getInstructions() { return instructions; }
    public List<RecipeItem> getItems() { return items; }
}
