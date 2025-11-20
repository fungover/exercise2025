package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.UserDto;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@Valid @RequestBody UserDto customizedUser) {
       userService.createUser(customizedUser);
    }

    @PostMapping("/user/create")
    public String createUser(@Valid @ModelAttribute UserDto user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "Username and password are mandatory";
        }

        userService.createUser(user);

        return "User created";

    }

}
