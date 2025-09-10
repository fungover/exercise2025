package org.example;

import java.time.OffsetDateTime;
import java.util.Map;

public class ConsumptionCalculator {

    public static void calculateTotalCost(PriceEntry[] prices, Map<String, Double> consumption) {
                double total = 0;
                int matched = 0;

                Map<OffsetDateTime, Double> consumptionByHour = new java.util.HashMap<>();
                for (Map.Entry<String, Double> e : consumption.entrySet()) {
                        try {
                                OffsetDateTime cons = OffsetDateTime.parse(e.getKey())
                                                .truncatedTo(java.time.temporal.ChronoUnit.HOURS);
                                consumptionByHour.merge(cons, e.getValue(), Double::sum);
                            } catch (Exception ex) {
                                System.err.println("Time parse failed for consumption entry " + e.getKey() + ": " + ex.getMessage());
                            }
                    }
                        for (PriceEntry p : prices) {
                        try {
                                OffsetDateTime priceHour = OffsetDateTime.parse(p.time_start)
                                               .truncatedTo(java.time.temporal.ChronoUnit.HOURS);
                                Double kWh = consumptionByHour.get(priceHour);
                                if (kWh != null) {
                                        total += p.SEK_per_kWh * kWh;
                                        matched++;
                                    }
                            } catch (Exception e) {
                                System.err.println("Time parse failed for " + p.time_start + ": " + e.getMessage());
                            }
                    }
                        System.out.printf("Matched %d hours from consumption data%n", matched);
                        System.out.printf("Total cost: %.2f SEK%n", total);
            }
}
