package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("welcome", "Welcome to the Book's book store");
        return "index";
    }

}
