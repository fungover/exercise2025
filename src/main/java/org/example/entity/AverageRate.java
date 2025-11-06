package org.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class AverageRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Många räntor kan tillhöra en bank
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    @JsonBackReference
    private Bank bank;

    // Själva räntesatsen (t.ex. 3.25 %)
    @Positive
    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal rate;

    // Datum då räntan gäller från
    @Column(nullable = false)
    private LocalDate date;

    // Tom konstruktor krävs av JPA
    public AverageRate() {}

    // Praktisk konstruktor
    public AverageRate(Bank bank, double rate, LocalDate date) {
        this.bank = bank;
        this.rate = BigDecimal.valueOf(rate);
        this.date = date;
    }

    // --- Getters & setters ---
    public Long getId() { return id; }

    public Bank getBank() { return bank; }
    public void setBank(Bank bank) { this.bank = bank; }

    public BigDecimal getRate() { return rate; }
    public void setRate(BigDecimal rate) { this.rate = rate; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}