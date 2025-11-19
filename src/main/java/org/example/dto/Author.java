package org.example.dto;

import jakarta.validation.constraints.NotEmpty;

public record Author(
        @NotEmpty(message = "Firstname is mandatory")
        String firstName,
        @NotEmpty(message = "Lastname is mandatory")
        String lastName) {
}
