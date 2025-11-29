package org.example;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.example.entities.Food;

import java.util.List;

public record Cat(
        @NotBlank(message = "Name is required") String name,
        @Min(value = 0, message = "Age must be 0 or greater") int age,
        List<Food> foodList) {
}
