package org.example.controllers;

import org.example.services.UserService;
import org.example.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(org.springframework.ui.Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/change-password")
    public String showForm(@RequestParam String username, Model model) {
        model.addAttribute("username", username);
        return "change-password";
    }

    @PutMapping("/change-password")
    public String handlePasswordChange(
            @RequestParam String username,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model
    ) {
        try {
            userService.updatePassword(username, currentPassword, newPassword, confirmPassword);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("username", username);
            model.addAttribute("error", e.getMessage());
            return "change-password";
        }
    }
}
