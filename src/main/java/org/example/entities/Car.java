package org.example.entities;

public record Car(String brand, String model,
                  String color, int year) implements Vehicle {}
