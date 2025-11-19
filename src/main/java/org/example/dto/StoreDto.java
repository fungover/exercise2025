package org.example.dto;

import jakarta.validation.constraints.NotEmpty;

public record StoreDto(@NotEmpty(message = "Store name must be inserted") String name) {
}
