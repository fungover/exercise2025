package org.example.controller;

import jakarta.validation.Valid;
import org.example.entity.AverageRate;
import org.example.repository.AverageRateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class AverageRateController {

    private final AverageRateRepository averageRateRepository;

    // Spring ger rätt repository automatiskt
    public AverageRateController(AverageRateRepository averageRateRepository) {
        this.averageRateRepository = averageRateRepository;
    }

    // GET: Hämta alla poster
    @GetMapping
    public List<AverageRate> getAllRates() {
        return averageRateRepository.findAll();
    }

    // POST: Lägg till en ny post
    @PostMapping
    public AverageRate createRate(@Valid @RequestBody AverageRate averageRate) {
        return averageRateRepository.save(averageRate);
    }
}
