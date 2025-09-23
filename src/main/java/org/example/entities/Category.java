package org.example.entities;

public enum Category {
    ELECTRONICS,
    CLOTHING,
    BOOKS,
    FOOD,
    TOYS;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}