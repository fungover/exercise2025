package org.example;

import java.time.OffsetDateTime;
import java.util.Map;

public class ConsumptionCalculator {

    public static void calculateTotalCost(PriceEntry[] prices, Map<String, Double> consumption) {
        double total = 0;
        int matched = 0;

        for (PriceEntry p : prices) {
            try {
                OffsetDateTime priceTime = OffsetDateTime.parse(p.time_start);

                for (Map.Entry<String, Double> entry : consumption.entrySet()) {
                    OffsetDateTime consTime = OffsetDateTime.parse(entry.getKey());

                    if (priceTime.getHour() == consTime.getHour()) {
                        total += p.SEK_per_kWh * entry.getValue();
                        matched++;
                    }
                }
            } catch (Exception e) {
                System.err.println("Time parse failed for " + p.time_start + ": " + e.getMessage());
            }
        }

        System.out.printf("Matched %d hours from consumption data%n", matched);
        System.out.printf("Total cost: %.2f SEK%n", total);
    }
}
