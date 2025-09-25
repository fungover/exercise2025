package org.example.entities;

public record Motorcycle(String brand, String model,
                  String color, int year) implements Vehicle {}
