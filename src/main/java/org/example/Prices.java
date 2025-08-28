package org.example;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class Prices {

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void showAllPrices(List<PriceEntry> prices) {
        if (prices == null || prices.isEmpty()) {
            System.out.println("Inga priser tillgängliga.");
            return;
        }

        System.out.println("Alla priser:");
        for (PriceEntry entry : prices) {
            System.out.printf("%s - %.2f kr/kWh%n",
                    entry.startTime().format(timeFormatter),
                    entry.pricePerKWh());
        }
    }

    public static void showAveragePrice(List<PriceEntry> prices) {
        // If there aren't any prices available, show a message to the user
        if (prices == null || prices.isEmpty()) {
            System.out.println("Inga priser tillgängliga.");
            return;
        }
     // Add all prices in list and count meanprice
        double sum = 0;
        for (PriceEntry entry : prices) {
            sum += entry.pricePerKWh();
        }

        double average = sum / prices.size();
        System.out.printf("Medelpris: %.2f kr/kWh%n", average);
    }

    public static void showMinAndMaxPrice(List<PriceEntry> prices) {
        if (prices == null || prices.isEmpty()) {
            System.out.println("Inga priser tillgängliga.");
            return;
        }
        // Starting values for the cheapest and most expensive hours
        PriceEntry minEntry = prices.get(0);
        PriceEntry maxEntry = prices.get(0);

        // loop through all prices to find the cheapest and most expensive
        for (PriceEntry entry : prices) {
            if (entry.pricePerKWh() < minEntry.pricePerKWh()) {
                minEntry = entry;
            }
            if (entry.pricePerKWh() > maxEntry.pricePerKWh()) {
                maxEntry = entry;
            }
        }

        System.out.println("Billigaste timmen:");
        System.out.printf("%s - %.2f kr/kWh%n",
                minEntry.startTime().format(timeFormatter),
                minEntry.pricePerKWh());

        System.out.println("Dyraste timmen:");
        System.out.printf("%s - %.2f kr/kWh%n",
                maxEntry.startTime().format(timeFormatter),
                maxEntry.pricePerKWh());
    }

    public static void findBestChargingTime(List<PriceEntry> prices, int durationHours) {
        if (prices == null || prices.size() < durationHours) {
            System.out.println("Inte tillräckligt med prisdata för " + durationHours + " timmar.");
            return;
        }

        // cheapest total so far
        double minSum = 0;
        // start index of cheapest window
        int minStartIndex = 0;

        // sum the first durationHours prices
        for (int i = 0; i < durationHours; i++) {
            minSum += prices.get(i).pricePerKWh();
        }

        double currentSum = minSum;

        // sliding window
        for (int i = 1; i <= prices.size() - durationHours; i++) {

            currentSum = currentSum - prices.get(i - 1).pricePerKWh() + prices.get(i + durationHours - 1).pricePerKWh();

            if (currentSum < minSum) {
                minSum = currentSum;
                minStartIndex = i;
            }
        }

        System.out.println("Bästa laddningstid är: " + durationHours + " timmar:");
        for (int i = minStartIndex; i < minStartIndex + durationHours; i++) {
            PriceEntry entry = prices.get(i);
            System.out.printf("%s - %.2f kr/kWh%n",
                    entry.startTime().format(timeFormatter),
                    entry.pricePerKWh());
        }

    }
}
