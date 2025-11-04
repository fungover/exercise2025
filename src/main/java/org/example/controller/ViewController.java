
package org.example.controller;

import org.example.model.Product;
import org.example.model.Location;
import org.example.repository.ProductRepository;
import org.example.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/")
    public String showProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("locations", locationRepository.findAll());
        model.addAttribute("product", new Product());
        return "index";
    }

    @PostMapping("/products/form")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/";
    }
}

