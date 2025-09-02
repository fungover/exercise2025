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


    public static PricePerHour findMostExpensiveHour(PricePerHour[] prices){
        PricePerHour maxHour = prices[0];
        for (PricePerHour p : prices)
            if (p.SEK_per_kWh() > maxHour.SEK_per_kWh())
                maxHour = p;
        return maxHour;
    }


    public static PricePerHour findCheapestHour(PricePerHour[] prices){
        PricePerHour minHour = prices[0];
        for (PricePerHour p : prices)
            if (p.SEK_per_kWh() < minHour.SEK_per_kWh())
                minHour = p;
        return minHour;
    }

}
