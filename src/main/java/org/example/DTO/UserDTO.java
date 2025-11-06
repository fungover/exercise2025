package org.example.DTO;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank(message = "Username cannot be blank")
        String user,
        @NotBlank(message = "Password cannot be blank")
        String password,
        @NotBlank(message = "Role cannot be blank")
        String role
) {
}
