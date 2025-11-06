package org.example.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class AverageRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bankName;
    private BigDecimal rate;
    private LocalDate date;

    // Tom konstruktor - Obligatoriskt för JPA
    public AverageRate() {}

    // Hjälpkonstruktor för att skapa objekt enklare i kod/tester
    public AverageRate(String bankName, BigDecimal rate, LocalDate date) {
        this.bankName = bankName;
        this.rate = rate;
        this.date = date;
    }

    // Getters och Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
