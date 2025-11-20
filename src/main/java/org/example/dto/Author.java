package org.example.dto;

import jakarta.validation.constraints.NotEmpty;

public record Author(
        @NotEmpty(message = "First name is mandatory")
        String firstName,
        @NotEmpty(message = "Last name is mandatory")
        String lastName) {
}
