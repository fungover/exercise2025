package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record User(Long id,
                   String name,
                   @NotBlank @NotNull String password,
                   @NotBlank @NotNull String email,
                   List<Note> notes) {
}
