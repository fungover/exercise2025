package org.example;

public class Calculate {

    public static double calculateMean(PricePerHour[] prices) {
        double sum = 0;
        double mean;

        for (PricePerHour p : prices) {
            sum += p.SEK_per_kWh();
        }
        mean = sum / prices.length;
        return mean;
    }


    public static PricePerHour findMostExpensiveHour(PricePerHour[] prices) {
        PricePerHour best = prices[0];
        for (PricePerHour p : prices)
            if (p.SEK_per_kWh() > best.SEK_per_kWh() ||
                    p.SEK_per_kWh() == best.SEK_per_kWh() &&
                            best.time_start().compareTo(p.time_start()) > 0)
                best = p;
        return best;
    }

    public static PricePerHour findCheapestHour(PricePerHour[] prices) {
        PricePerHour best = prices[0];
        for (PricePerHour p : prices)
            if (p.SEK_per_kWh() < best.SEK_per_kWh() || (p.SEK_per_kWh() == best.SEK_per_kWh()
                    && best.time_start().compareTo(p.time_start()) > 0))
                best = p;
        return best;
    }
}
