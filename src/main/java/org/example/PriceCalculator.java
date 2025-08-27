package org.example;

//TODO implement identifier for best charge hours in span of 2, 4 or 8 hours (sliding window algorithm)
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriceCalculator {

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
        if (allPrices.size() < chargingHours) {
            return null;
        }

        List<ApiClient.ElectricityPrice> availablePrices = filterPastHours(allPrices);

        if (availablePrices.size() < chargingHours) {
            return null;
        }

        double bestTotalCost = Double.MAX_VALUE;
        int bestStartIndex = 0;

        // Sliding window algoritm (slides one position at a time)
        for (int i = 0; i <= availablePrices.size() - chargingHours; i++) {
            double windowCost = 0;

            for (int j = i; j < i + chargingHours; j++) {
                windowCost += availablePrices.get(j).SEK_per_kWh();
            }

            if (windowCost < bestTotalCost) {
                bestTotalCost = windowCost;
                bestStartIndex = i;
            }
        }

        List<ApiClient.ElectricityPrice> bestWindow = new ArrayList<>();
        for (int i = bestStartIndex; i < bestStartIndex + chargingHours; i++) {
            bestWindow.add(availablePrices.get(i));
        }

        return new ChargingWindow(bestWindow, bestTotalCost / chargingHours);
    }

    public static List<ApiClient.ElectricityPrice> combineAllAvailablePrices(
            ApiClient.ElectricityPrice[] todayPrices,
            ApiClient.ElectricityPrice[] tomorrowPrices) {

        List<ApiClient.ElectricityPrice> combinedPrices = new ArrayList<>();

        if (todayPrices != null) {
            for (ApiClient.ElectricityPrice price : todayPrices) {
                combinedPrices.add(price);
            }
        }

        if (tomorrowPrices != null) {
            for (ApiClient.ElectricityPrice price : tomorrowPrices) {
                combinedPrices.add(price);
            }
        }

        return combinedPrices;
    }

    private static List<ApiClient.ElectricityPrice> filterPastHours(List<ApiClient.ElectricityPrice> prices) {
        LocalDateTime now = LocalDateTime.now();
        List<ApiClient.ElectricityPrice> availablePrices = new ArrayList<>();

        for (ApiClient.ElectricityPrice price : prices) {
            try {
                ZonedDateTime priceStart = ZonedDateTime.parse(price.time_start());
                LocalDateTime priceStartLocal = priceStart.toLocalDateTime();

                if (priceStartLocal.isAfter(now) || priceStartLocal.equals(now.withMinute(0).withSecond(0).withNano(0))) {
                    availablePrices.add(price);
                }
            } catch (Exception e) {

            }
        }

        return availablePrices;
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

            ZonedDateTime start = ZonedDateTime.parse(firstHour.time_start());
            ZonedDateTime end = ZonedDateTime.parse(lastHour.time_end());

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


