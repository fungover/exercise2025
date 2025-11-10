package org.example;

import org.example.entities.Food;

import java.util.List;

public record Pet(String name, String type, List<Food> foodList) {
}
