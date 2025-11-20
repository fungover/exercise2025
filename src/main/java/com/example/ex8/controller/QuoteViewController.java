package com.example.ex8.controller;

import com.example.ex8.repository.QuoteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuoteViewController {

    private final QuoteRepository quoteRepository;

    public QuoteViewController(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @GetMapping("/quotes")
    public String showQuotes(Model model) {
        model.addAttribute("quotes", quoteRepository.findAll());
        return "quotes";
    }
}
