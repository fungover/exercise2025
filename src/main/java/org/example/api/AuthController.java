package org.example.api;

import jakarta.validation.Valid;
import org.example.api.dto.RegisterRequest;
import org.example.api.dto.UserView;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService users;

    public AuthController(UserService users) {
        this.users = users;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserView register(@RequestBody @Valid RegisterRequest req) {
        return users.register(req);
    }
}

