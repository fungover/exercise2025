package com.bolaneradarmini.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AverageRate> rates = new ArrayList<>();

    // Tom konstruktor krävs av JPA
    public Bank() {}

    // Praktisk konstruktor
    public Bank(String name) {
        this.name = name;
    }

    // --- Getters & setters ---
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<AverageRate> getRates() { return rates; }

    // --- Hjälpmetoder för säker hantering av relationer ---
    public void addRate(AverageRate rate) {
        rates.add(rate);
        rate.setBank(this);
    }

    public void removeRate(AverageRate rate) {
        rates.remove(rate);
        rate.setBank(null);
    }

    public void setRates(List<AverageRate> rates) {
        this.rates.clear();
        if (rates != null) {
            rates.forEach(this::addRate);
        }
    }
}