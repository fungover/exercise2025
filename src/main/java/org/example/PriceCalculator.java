package org.example;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriceCalculator {

    private static final ZoneId SWEDISH_ZONE = ZoneId.of("Europe/Stockholm");

    public static double calculateAveragePrice(List<ApiClient.ElectricityPrice> prices) {
        return prices.stream()
                .mapToDouble(ApiClient.ElectricityPrice::SEK_per_kWh)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Prislistan kan inte vara tom"));
    }

    public static ApiClient.ElectricityPrice findMaxPrice(List<ApiClient.ElectricityPrice> prices) {
        return prices.stream()
                .max(Comparator.comparingDouble(ApiClient.ElectricityPrice::SEK_per_kWh))
                .orElseThrow(() -> new IllegalArgumentException("Prislistan kan inte vara tom"));
    }

    public static ApiClient.ElectricityPrice findMinPrice(List<ApiClient.ElectricityPrice> prices) {
        return prices.stream()
                .min(Comparator.comparingDouble(ApiClient.ElectricityPrice::SEK_per_kWh))
                .orElseThrow(() -> new IllegalArgumentException("Prislistan kan inte vara tom"));
    }

    public static ChargingWindow findBestChargingWindow(List<ApiClient.ElectricityPrice> allPrices, int chargingHours) {
        if (chargingHours <= 0) {
            throw new IllegalArgumentException("Antal laddningstimmar måste vara minst 1");
        }

        if (allPrices.size() < chargingHours) {
            return null;
        }

        List<ApiClient.ElectricityPrice> availablePrices = filterPastHours(allPrices);

        if (availablePrices.size() < chargingHours) {
            return null;
        }

        double windowCost = 0;
        for (int i = 0; i < chargingHours; i++) {
            windowCost += availablePrices.get(i).SEK_per_kWh();
        }

        double bestTotalCost = windowCost;
        int bestStartIndex = 0;

        for (int i = chargingHours; i < availablePrices.size(); i++) {
            windowCost += availablePrices.get(i).SEK_per_kWh() - availablePrices.get(i - chargingHours).SEK_per_kWh();
            if (windowCost < bestTotalCost) {
                bestTotalCost = windowCost;
                bestStartIndex = i - chargingHours + 1;
            }
        }

        List<ApiClient.ElectricityPrice> bestWindow =
                availablePrices.subList(bestStartIndex, bestStartIndex + chargingHours);

        return new ChargingWindow(bestWindow, bestTotalCost / chargingHours);
    }

    public static List<ApiClient.ElectricityPrice> combineAllAvailablePrices(
            ApiClient.ElectricityPrice[] todayPrices,
            ApiClient.ElectricityPrice[] tomorrowPrices) {

        List<ApiClient.ElectricityPrice> combinedPrices = new ArrayList<>();

        if (todayPrices != null) combinedPrices.addAll(List.of(todayPrices));
        if (tomorrowPrices != null) combinedPrices.addAll(List.of(tomorrowPrices));

        return combinedPrices;
    }

    private static List<ApiClient.ElectricityPrice> filterPastHours(List<ApiClient.ElectricityPrice> prices) {
        ZonedDateTime now = ZonedDateTime.now(SWEDISH_ZONE)
                .withMinute(0).withSecond(0).withNano(0);

        List<ApiClient.ElectricityPrice> availablePrices = new ArrayList<>();

        for (ApiClient.ElectricityPrice price : prices) {
            try {
                ZonedDateTime priceStart = ZonedDateTime.parse(price.time_start())
                        .withZoneSameInstant(SWEDISH_ZONE);

                if (!priceStart.isBefore(now)) {
                    availablePrices.add(price);
                }
            } catch (Exception e) {
                System.err.println("Felaktigt datumformat: " + price.time_start());
            }
        }

        availablePrices.sort(Comparator.comparing(p ->
                ZonedDateTime.parse(p.time_start()).withZoneSameInstant(SWEDISH_ZONE)
        ));
        return availablePrices;
    }
    }

    public record ChargingWindow(
            List<ApiClient.ElectricityPrice> hours,
            double averagePrice
    ) {
        public String getFormattedTimeRange() {
            if (hours.isEmpty()) {
                return "Inget laddfönster tillgängligt";
            }

            ApiClient.ElectricityPrice firstHour = hours.get(0);
            ApiClient.ElectricityPrice lastHour = hours.get(hours.size() - 1);

            ZonedDateTime start = ZonedDateTime.parse(firstHour.time_start())
                    .withZoneSameInstant(SWEDISH_ZONE);
            ZonedDateTime end = ZonedDateTime.parse(lastHour.time_end())
                    .withZoneSameInstant(SWEDISH_ZONE);

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");

            String startDate = start.format(dateFormatter);
            String endDate = end.format(dateFormatter);

            if (startDate.equals(endDate)) {
                return String.format("kl %s - %s",
                        start.format(timeFormatter), end.format(timeFormatter));
            } else {
                return String.format("%s kl %s - %s kl %s",
                        startDate, start.format(timeFormatter), endDate, end.format(timeFormatter));
            }
        }
    }
}
