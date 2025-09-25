package org.example.model;

public class MeanPrice {
    private final double sek;
    private final double eur;

    public MeanPrice(double sek, double eur) {
        this.sek = sek;
        this.eur = eur;
    }

    public double getSek() {
        return sek;
    }
    public double getEur() {
        return eur;
    }

    @Override
    public String toString() {
        return String.format("Mean price: SEK %.2f EUR %.2f", sek, eur);
    }
}
