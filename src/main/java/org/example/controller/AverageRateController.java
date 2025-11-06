package org.example.controller;

import jakarta.validation.Valid;
import org.example.entity.AverageRate;
import org.example.repository.AverageRateRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    // DELETE: Tar bort alla räntor
    @DeleteMapping
    public String deleteAllRates() {
        averageRateRepository.deleteAll();
        return "All rates have been deleted";
    }

    // POST: Ladda in exempeldata
    @PostMapping("/load-example-data")
    public String loadExampleData() {
        averageRateRepository.deleteAll();

        List<AverageRate> rates = List.of(
                // Swedbank
                new AverageRate("Swedbank", 3.74, LocalDate.of(2024, 11, 1)),
                new AverageRate("Swedbank", 3.52, LocalDate.of(2024, 12, 1)),
                new AverageRate("Swedbank", 3.39, LocalDate.of(2025, 1, 1)),
                new AverageRate("Swedbank", 3.20, LocalDate.of(2025, 2, 1)),
                new AverageRate("Swedbank", 3.15, LocalDate.of(2025, 3, 1)),
                new AverageRate("Swedbank", 3.13, LocalDate.of(2025, 4, 1)),
                new AverageRate("Swedbank", 3.14, LocalDate.of(2025, 5, 1)),
                new AverageRate("Swedbank", 3.03, LocalDate.of(2025, 6, 1)),
                new AverageRate("Swedbank", 2.90, LocalDate.of(2025, 7, 1)),
                new AverageRate("Swedbank", 2.85, LocalDate.of(2025, 8, 1)),
                new AverageRate("Swedbank", 2.72, LocalDate.of(2025, 9, 1)),
                new AverageRate("Swedbank", 2.65, LocalDate.of(2025, 10, 1)),

                // Nordea
                new AverageRate("Nordea", 3.74, LocalDate.of(2024, 11, 1)),
                new AverageRate("Nordea", 3.53, LocalDate.of(2024, 12, 1)),
                new AverageRate("Nordea", 3.40, LocalDate.of(2025, 1, 1)),
                new AverageRate("Nordea", 3.20, LocalDate.of(2025, 2, 1)),
                new AverageRate("Nordea", 3.16, LocalDate.of(2025, 3, 1)),
                new AverageRate("Nordea", 3.15, LocalDate.of(2025, 4, 1)),
                new AverageRate("Nordea", 3.15, LocalDate.of(2025, 5, 1)),
                new AverageRate("Nordea", 3.01, LocalDate.of(2025, 6, 1)),
                new AverageRate("Nordea", 2.89, LocalDate.of(2025, 7, 1)),
                new AverageRate("Nordea", 2.90, LocalDate.of(2025, 8, 1)),
                new AverageRate("Nordea", 2.81, LocalDate.of(2025, 9, 1)),
                new AverageRate("Nordea", 2.68, LocalDate.of(2025, 10, 1)),

                // Handelsbanken
                new AverageRate("Handelsbanken", 3.87, LocalDate.of(2024, 10, 1)),
                new AverageRate("Handelsbanken", 3.86, LocalDate.of(2024, 11, 1)),
                new AverageRate("Handelsbanken", 3.52, LocalDate.of(2024, 12, 1)),
                new AverageRate("Handelsbanken", 3.38, LocalDate.of(2025, 1, 1)),
                new AverageRate("Handelsbanken", 3.22, LocalDate.of(2025, 2, 1)),
                new AverageRate("Handelsbanken", 3.11, LocalDate.of(2025, 3, 1)),
                new AverageRate("Handelsbanken", 3.06, LocalDate.of(2025, 4, 1)),
                new AverageRate("Handelsbanken", 3.07, LocalDate.of(2025, 5, 1)),
                new AverageRate("Handelsbanken", 3.01, LocalDate.of(2025, 6, 1)),
                new AverageRate("Handelsbanken", 2.81, LocalDate.of(2025, 7, 1)),
                new AverageRate("Handelsbanken", 2.83, LocalDate.of(2025, 8, 1)),
                new AverageRate("Handelsbanken", 2.77, LocalDate.of(2025, 9, 1)),

                // SEB
                new AverageRate("SEB", 4.02, LocalDate.of(2024, 10, 1)),
                new AverageRate("SEB", 3.65, LocalDate.of(2024, 11, 1)),
                new AverageRate("SEB", 3.32, LocalDate.of(2024, 12, 1)),
                new AverageRate("SEB", 3.25, LocalDate.of(2025, 1, 1)),
                new AverageRate("SEB", 3.02, LocalDate.of(2025, 2, 1)),
                new AverageRate("SEB", 3.02, LocalDate.of(2025, 3, 1)),
                new AverageRate("SEB", 3.01, LocalDate.of(2025, 4, 1)),
                new AverageRate("SEB", 3.04, LocalDate.of(2025, 5, 1)),
                new AverageRate("SEB", 2.91, LocalDate.of(2025, 6, 1)),
                new AverageRate("SEB", 2.88, LocalDate.of(2025, 7, 1)),
                new AverageRate("SEB", 2.86, LocalDate.of(2025, 8, 1)),
                new AverageRate("SEB", 2.68, LocalDate.of(2025, 9, 1)),

                // SBAB
                new AverageRate("SBAB", 3.60, LocalDate.of(2024, 11, 1)),
                new AverageRate("SBAB", 3.46, LocalDate.of(2024, 12, 1)),
                new AverageRate("SBAB", 3.28, LocalDate.of(2025, 1, 1)),
                new AverageRate("SBAB", 3.11, LocalDate.of(2025, 2, 1)),
                new AverageRate("SBAB", 3.15, LocalDate.of(2025, 3, 1)),
                new AverageRate("SBAB", 3.17, LocalDate.of(2025, 4, 1)),
                new AverageRate("SBAB", 3.10, LocalDate.of(2025, 5, 1)),
                new AverageRate("SBAB", 3.02, LocalDate.of(2025, 6, 1)),
                new AverageRate("SBAB", 2.85, LocalDate.of(2025, 7, 1)),
                new AverageRate("SBAB", 2.86, LocalDate.of(2025, 8, 1)),
                new AverageRate("SBAB", 2.84, LocalDate.of(2025, 9, 1)),
                new AverageRate("SBAB", 2.69, LocalDate.of(2025, 10, 1))
        );

        averageRateRepository.saveAll(rates);
        return "Example data for Swedbank loaded (" + rates.size() + " records)";
    }

}
