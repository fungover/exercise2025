package com.bolaneradarmini.controller;

import jakarta.validation.Valid;
import com.bolaneradarmini.entity.AverageRate;
import com.bolaneradarmini.entity.Bank;
import com.bolaneradarmini.repository.AverageRateRepository;
import com.bolaneradarmini.repository.BankRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class AverageRateController {

    private final AverageRateRepository averageRateRepository;
    private final BankRepository bankRepository;


    // Spring ger rätt repository automatiskt
    public AverageRateController(AverageRateRepository averageRateRepository, BankRepository bankRepository) {
        this.averageRateRepository = averageRateRepository;
        this.bankRepository = bankRepository;
    }

    // GET: Hämta alla poster
    @GetMapping
    public List<AverageRate> getAllRates() {
        return averageRateRepository.findAll();
    }

    // POST: Lägg till en ny post
    @PostMapping
    public AverageRate createRate(@Valid @RequestBody AverageRate averageRate) {
        // Kolla om banken finns i JSON
        if (averageRate.getBank() == null || averageRate.getBank().getName() == null) {
            throw new RuntimeException("Bank name must be provided in the JSON under bank.name");
        }

        // Leta upp banken i databasen via namnet
        var existingBank = bankRepository.findByName(averageRate.getBank().getName())
                .orElseThrow(() -> new RuntimeException("Bank not found " + averageRate.getBank().getName()));

        // Koppla dn befintliga banken till AVerageRate
        averageRate.setBank(existingBank);

        // Spara snitträntan
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

        // Hämta befintliga banker (som redan finns via BankController)
        Bank swedbank = bankRepository.findByName("Swedbank")
                .orElseThrow(() -> new RuntimeException("Swedbank not found"));
        Bank nordea = bankRepository.findByName("Nordea")
                .orElseThrow(() -> new RuntimeException("Nordea not found"));
        Bank handelsbanken = bankRepository.findByName("Handelsbanken")
                .orElseThrow(() -> new RuntimeException("Handelsbanken not found"));
        Bank seb = bankRepository.findByName("SEB")
                .orElseThrow(() -> new RuntimeException("SEB not found"));
        Bank sbab = bankRepository.findByName("SBAB")
                .orElseThrow(() -> new RuntimeException("SBAB not found"));

        // Skapa räntor kopplade till bankerna
        List<AverageRate> rates = List.of(
                // Swedbank
                new AverageRate(swedbank, 3.74, LocalDate.of(2024, 11, 1)),
                new AverageRate(swedbank, 3.52, LocalDate.of(2024, 12, 1)),
                new AverageRate(swedbank, 3.39, LocalDate.of(2025, 1, 1)),
                new AverageRate(swedbank, 3.20, LocalDate.of(2025, 2, 1)),
                new AverageRate(swedbank, 3.15, LocalDate.of(2025, 3, 1)),
                new AverageRate(swedbank, 3.13, LocalDate.of(2025, 4, 1)),
                new AverageRate(swedbank, 3.14, LocalDate.of(2025, 5, 1)),
                new AverageRate(swedbank, 3.03, LocalDate.of(2025, 6, 1)),
                new AverageRate(swedbank, 2.90, LocalDate.of(2025, 7, 1)),
                new AverageRate(swedbank, 2.85, LocalDate.of(2025, 8, 1)),
                new AverageRate(swedbank, 2.72, LocalDate.of(2025, 9, 1)),
                new AverageRate(swedbank, 2.65, LocalDate.of(2025, 10, 1)),

                // Nordea
                new AverageRate(nordea, 3.74, LocalDate.of(2024, 11, 1)),
                new AverageRate(nordea, 3.53, LocalDate.of(2024, 12, 1)),
                new AverageRate(nordea, 3.40, LocalDate.of(2025, 1, 1)),
                new AverageRate(nordea, 3.20, LocalDate.of(2025, 2, 1)),
                new AverageRate(nordea, 3.16, LocalDate.of(2025, 3, 1)),
                new AverageRate(nordea, 3.15, LocalDate.of(2025, 4, 1)),
                new AverageRate(nordea, 3.15, LocalDate.of(2025, 5, 1)),
                new AverageRate(nordea, 3.01, LocalDate.of(2025, 6, 1)),
                new AverageRate(nordea, 2.89, LocalDate.of(2025, 7, 1)),
                new AverageRate(nordea, 2.90, LocalDate.of(2025, 8, 1)),
                new AverageRate(nordea, 2.81, LocalDate.of(2025, 9, 1)),
                new AverageRate(nordea, 2.68, LocalDate.of(2025, 10, 1)),

                // Handelsbanken
                new AverageRate(handelsbanken, 3.87, LocalDate.of(2024, 10, 1)),
                new AverageRate(handelsbanken, 3.86, LocalDate.of(2024, 11, 1)),
                new AverageRate(handelsbanken, 3.52, LocalDate.of(2024, 12, 1)),
                new AverageRate(handelsbanken, 3.38, LocalDate.of(2025, 1, 1)),
                new AverageRate(handelsbanken, 3.22, LocalDate.of(2025, 2, 1)),
                new AverageRate(handelsbanken, 3.11, LocalDate.of(2025, 3, 1)),
                new AverageRate(handelsbanken, 3.06, LocalDate.of(2025, 4, 1)),
                new AverageRate(handelsbanken, 3.07, LocalDate.of(2025, 5, 1)),
                new AverageRate(handelsbanken, 3.01, LocalDate.of(2025, 6, 1)),
                new AverageRate(handelsbanken, 2.81, LocalDate.of(2025, 7, 1)),
                new AverageRate(handelsbanken, 2.83, LocalDate.of(2025, 8, 1)),
                new AverageRate(handelsbanken, 2.77, LocalDate.of(2025, 9, 1)),

                // SEB
                new AverageRate(seb, 4.02, LocalDate.of(2024, 10, 1)),
                new AverageRate(seb, 3.65, LocalDate.of(2024, 11, 1)),
                new AverageRate(seb, 3.32, LocalDate.of(2024, 12, 1)),
                new AverageRate(seb, 3.25, LocalDate.of(2025, 1, 1)),
                new AverageRate(seb, 3.02, LocalDate.of(2025, 2, 1)),
                new AverageRate(seb, 3.02, LocalDate.of(2025, 3, 1)),
                new AverageRate(seb, 3.01, LocalDate.of(2025, 4, 1)),
                new AverageRate(seb, 3.04, LocalDate.of(2025, 5, 1)),
                new AverageRate(seb, 2.91, LocalDate.of(2025, 6, 1)),
                new AverageRate(seb, 2.88, LocalDate.of(2025, 7, 1)),
                new AverageRate(seb, 2.86, LocalDate.of(2025, 8, 1)),
                new AverageRate(seb, 2.68, LocalDate.of(2025, 9, 1)),

                // SBAB
                new AverageRate(sbab, 3.60, LocalDate.of(2024, 11, 1)),
                new AverageRate(sbab, 3.46, LocalDate.of(2024, 12, 1)),
                new AverageRate(sbab, 3.28, LocalDate.of(2025, 1, 1)),
                new AverageRate(sbab, 3.11, LocalDate.of(2025, 2, 1)),
                new AverageRate(sbab, 3.15, LocalDate.of(2025, 3, 1)),
                new AverageRate(sbab, 3.17, LocalDate.of(2025, 4, 1)),
                new AverageRate(sbab, 3.10, LocalDate.of(2025, 5, 1)),
                new AverageRate(sbab, 3.02, LocalDate.of(2025, 6, 1)),
                new AverageRate(sbab, 2.85, LocalDate.of(2025, 7, 1)),
                new AverageRate(sbab, 2.86, LocalDate.of(2025, 8, 1)),
                new AverageRate(sbab, 2.84, LocalDate.of(2025, 9, 1)),
                new AverageRate(sbab, 2.69, LocalDate.of(2025, 10, 1))
        );

        averageRateRepository.saveAll(rates);
        return "Example data with " + rates.size() + " rates across 5 banks loaded.";
    }
}
