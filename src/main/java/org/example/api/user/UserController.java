package org.example.api.user;

import jakarta.validation.Valid;
import org.example.dto.request.user.CreateUserRequest;
import org.example.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public String create(@Valid @RequestBody CreateUserRequest request){

        return  userService.createUser(request.getUsername(), request.getPassword());
    }
}
