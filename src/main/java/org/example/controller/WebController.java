package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "\uD83D\uDC3E Pet Adoption Service \uD83D\uDC3E");

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
