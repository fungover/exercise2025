package org.example.model;

public class MeanPrice {
    double sek;
    double eur;

    public MeanPrice(double sek, double eur) {
        this.sek = sek;
        this.eur = eur;
    }

    @Override
    public String toString() {
        return String.format("Mean price: SEK %.2f EUR %.2f", sek, eur);
    }
}
