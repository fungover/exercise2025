package com.example.ex8.controller;

import com.example.ex8.entities.Quote;
import com.example.ex8.repository.QuoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Quote> create(@RequestBody Quote quote) {
        Quote saved = repo.save(quote);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}