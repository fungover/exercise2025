package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("secret")
public class SecretPageController {

    @GetMapping
    public String index(Model model){
        model.addAttribute("secret", "Welcome to the secret page. You are know logged in as admin.");
        return "secret";
    }

}
