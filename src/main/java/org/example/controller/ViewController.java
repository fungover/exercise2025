package org.example.controller;

import org.example.entity.Animal;
import org.example.service.AnimalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


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

    @GetMapping("/animals/add")
    public String showAddAnimalForm() {
        return "add-animal";
    }

    @PostMapping("/animals/add")
    public String addAnimal(@ModelAttribute Animal animal) {
        animalService.save(animal);
        return "redirect:/animals";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

}
