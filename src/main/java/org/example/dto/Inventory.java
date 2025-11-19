package org.example.dto;

import jakarta.validation.constraints.Min;

public record Inventory(@Min(value = 0, message = "Value cant be less than 0") int amount) {
}
