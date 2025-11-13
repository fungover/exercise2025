package org.example.dto.user;

public record UserNew(Long id, String name, String email, String token, String refreshToken) {
}
