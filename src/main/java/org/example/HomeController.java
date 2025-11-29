package org.example;

import org.example.entities.Cat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final CatRepository repository;

    public HomeController(CatRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model) {
        var cats = repository.findAll();
        model.addAttribute("cats", cats);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/cats")
    public String addCat(@RequestParam String name, @RequestParam int age) {
        Cat cat = new Cat(name, age, java.util.List.of());
        repository.save(cat);
        return "redirect:/";
    }
}
