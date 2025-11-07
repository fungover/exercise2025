package org.example.controllers;

import org.example.repository.UserRepository;
import org.example.services.UserService;
import org.example.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "change-password";  // thymeleaf-view
    }


    //TODO: We need functionality to update the user password. This should be done with PUT
    //TODO: Fix error handling when log in.
    @PostMapping("/change-password")
    public String handlePasswordChange(
            @RequestParam String username,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model
    ) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("username", username);
            model.addAttribute("error", "Passwords do not match");
            return "change-password";
        }

        userService.createUser(username, newPassword, "ROLE_ADMIN");
        

        return "redirect:/login";
    }
}
