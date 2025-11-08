package org.example.mapper.user;

import org.example.dto.request.user.CreateUserRequest;
import org.example.dto.response.user.UserResponse;
import org.example.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static User toEntity(CreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .authorities("ROLE_USER")
                .build();

        return user;
    }

    public static UserResponse toResponse(User user) {
        UserResponse userResponse = new UserResponse(
                user.getUsername(),
                user.getApiKey()
        );

        return userResponse;
    }
}
