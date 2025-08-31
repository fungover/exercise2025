package org.example;
import java.io.IOException;
import java.time.LocalDate;

public class App {
    static void main(String[] args) {
        double sum = 0;
        double mean;
        double min = 0;
        double max = 0;
        Fetch fetch = new Fetch();
        String priceBracket = "SE3";

        try {
            // Today's prices
            PricePerHour[] today = fetch.fetch(LocalDate.now(), priceBracket);
            System.out.println("Dagens priser i " + priceBracket + ": ");
            for (PricePerHour p : today) {
                System.out.println(p.time_start() + " -- " + p.SEK_per_kWh() + " kr/kWh");
            }

            // Tomorrow's prices
            PricePerHour[] tomorrow = fetch.fetch(LocalDate.now().plusDays(1), priceBracket);
            System.out.println("\nMorgondagens priser i " + priceBracket + ": ");
            for (PricePerHour p : tomorrow) {
                System.out.println(p.time_start() + " -- " + p.SEK_per_kWh() + " kr/kWh");
            }

            // Calculate mean price for 24 hours
            for (PricePerHour p : today) {
                sum += p.SEK_per_kWh();
            }
            mean = sum / today.length;
            System.out.println(mean);

            // Identify the cheapest hour in the chosen price bracket
            for (PricePerHour price : today) {
                if (price.SEK_per_kWh() > max) {
                    max = price.SEK_per_kWh();
                }
            }
            
            for (PricePerHour price : today){

            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }



    }
    }

