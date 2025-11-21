package org.example.controllers;

import org.example.entities.Pet;
import org.example.services.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

    private final PetService petService;

    public WebController(PetService petService) {
        this.petService = petService;
    }

    // Home page displays all pets
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pets", petService.getAllPets(null, null, null, null, null));
        return "index";
    }

    // Create a new pet (admin only)
    @PostMapping("/pets/add")
    public String addPet(@RequestParam String name, @RequestParam String species, RedirectAttributes redirectAttributes) {

        name = name.trim();
        species = species.trim();

        if (name.isBlank() || species.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Name and Species cannot be blank!");
            return "redirect:/";
        }

        if (name.length() > 20 || species.length() > 20) {
            redirectAttributes.addFlashAttribute("error", "Name and Species must be under 20 characters!");
            return "redirect:/";
        }

        try {
            Pet pet = new Pet(name, species);
            petService.addPet(pet);
            redirectAttributes.addFlashAttribute("success", "New pet added!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create pet: " + e.getMessage());
        }
        return "redirect:/";
    }

    // Feed a pet
    @PostMapping("/pets/{id}/feed")
    public String feedPet(@PathVariable Long id) {
        petService.feedPet(id);
        return "redirect:/";
    }

    // Play with a pet
    @PostMapping("/pets/{id}/play")
    public String playWithPet(@PathVariable Long id) {
        petService.playWithPet(id);
        return "redirect:/";
    }

    // Delete a pet
    @PostMapping("/pets/{id}/delete")
    public String deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return "redirect:/";
    }

}
