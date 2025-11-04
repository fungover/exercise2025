package org.example.api.security;

import jakarta.validation.Valid;
import org.example.dto.request.user.CreateUserRequest;
import org.example.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class SecurityController {
    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public  String home(){
        return "This is Home";
    }

    @GetMapping("/director")
    public  String director(){
        return "This is Director";
    }

    @GetMapping("/admin")
    public  String admin(){
        return "This is Admin";
    }

    @PostMapping("/create")
    public String create(@Valid @RequestBody CreateUserRequest request){
        return  userService.createUser(request.getUsername(), request.getPassword());
    }
}
