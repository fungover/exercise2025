package org.example.api.user;

import jakarta.validation.Valid;
import org.example.dto.request.user.CreateUserRequest;
import org.example.dto.response.user.UserResponse;
import org.example.entities.User;
import org.example.mapper.user.UserMapper;
import org.example.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request){
        User user = UserMapper.toEntity(request);
        User newUser = userService.createUser(user);
        UserResponse response = UserMapper.toResponse(newUser);

        return ResponseEntity.status(201).body(response);
    }
}
