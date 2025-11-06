package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class AverageRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String bankName;

    @Positive
    private BigDecimal rate;

    private LocalDate date;

    // Tom konstruktor krävs av JPA
    public AverageRate() {}

    public AverageRate(String bankName, double rate, LocalDate date) {
        this.bankName = bankName;
        this.rate = BigDecimal.valueOf(rate);
        this.date = date;
    }

    // Getters och setters
    public Long getId() {
        return id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}