package org.example;

public class Calculate {

    public static double calculateMean(PricePerHour[] prices){
        double sum = 0;
        double mean;

        for (PricePerHour p : prices) {
            sum += p.SEK_per_kWh();
        }
        mean = sum / prices.length;
        return mean;
    }


    public static String findMostExpensiveHour(PricePerHour[] prices){
        double max = 0;
        String mostExpensiveHour = "";

        for (PricePerHour p : prices) {
            if (p.SEK_per_kWh() > max) {
                max = p.SEK_per_kWh();
                mostExpensiveHour = p.time_start() + " till " + p.time_end();
            }
        }
        return mostExpensiveHour;
    }


    public static String findCheapestHour(PricePerHour[] prices) {
        double min = Double.MAX_VALUE;
        double max = 0;
        String cheapestHour = "";

        for (PricePerHour p : prices) {
            if (p.SEK_per_kWh() < max && p.SEK_per_kWh() < min) {
                min = p.SEK_per_kWh();
                cheapestHour = p.time_start() + " till " + p.time_end();
            }
        }
        return cheapestHour;
    }

}
