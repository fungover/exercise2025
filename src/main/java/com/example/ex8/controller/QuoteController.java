package com.example.ex8.controller;

import com.example.ex8.entities.Quote;
import com.example.ex8.repository.QuoteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteRepository repo;

    public QuoteController(QuoteRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Quote> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Quote create(@RequestBody Quote quote) {
        return repo.save(quote);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}