package org.example;

import org.example.electricity.PriceData;

import java.time.Instant;

class App {
    public static void main(String[] args) {
        PriceData price = new PriceData(
                Instant.now(),
                Instant.now().plusSeconds(3600),
                522999,
                0.021,
                11.12
        );

        System.out.println(price);
        System.out.println("Pris i SEK: " + price.sekPerkWh());
    }
}
