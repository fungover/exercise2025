package org.example.controller;

import org.example.entity.Bank;
import org.example.repository.BankRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankRepository bankRepository;

    public BankController(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    // Hämta alla banker
    @GetMapping
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    // Lägg till ny bank
    @PostMapping
    public Bank createBank(@RequestBody Bank bank) {
        return bankRepository.save(bank);
    }

    // Ta bort alla banker
    @DeleteMapping
    public String deleteAllBanks() {
        bankRepository.deleteAll();
        return "All banks deleted";
    }

    // Ladda exempelbanker (exakt den kod du nämnde)
    @PostMapping("/load-example-banks")
    public String loadExampleBanks() {
        bankRepository.deleteAll();

        List<Bank> banks = List.of(
                new Bank("Swedbank"),
                new Bank("Nordea"),
                new Bank("Handelsbanken"),
                new Bank("SEB"),
                new Bank("SBAB")
        );

        bankRepository.saveAll(banks);

        return "Example banks loaded: " + banks.size();
    }
}