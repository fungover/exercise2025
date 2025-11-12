package org.example.controller;

import org.example.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * this will serve HTML pages
 */
@Controller
public class ViewController {
    private final AnimalService animalService;

    public ViewController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/animals")
    public String showAnimals(Model model) {
        model.addAttribute("animals", animalService.findAll());
        return "animals"; //this is animals.html in /templates
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

}
