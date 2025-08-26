package org.example;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PriceUtils {

    // Formatera tiden i ett enklare format (yyyy-MM-dd HH:mm)
    public static String formatDateTime(PricePoint p) {
        OffsetDateTime dateTime = OffsetDateTime.parse(p.time());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }

    // Formatera priset med max 3 decimaler
    public static String formatPrice(double value) {
        return String.format(Locale.ROOT, "%.3f", value);
    }

    // Skriver ut alla priser i listan
    public static void printAllPricesToday(List<PricePoint> prices) {
        for (PricePoint p : prices) {
            String hour = formatDateTime(p);
            System.out.println("Tid " + hour + " till priset " + p.price() + "kr/kWh");
        }
    }

    // Medelpris i listan
    public static double averagePrice(List<PricePoint> prices) {
        if (prices.isEmpty()) {
            return 0.0;
        }

        double sum = 0;

        for (PricePoint price : prices) {
            sum = sum + price.price();
        }

        return sum / prices.size();
    }

    // Billigaste timmen (vid lika pris: välj tidigast)
    public static PricePoint cheapestPrice(List<PricePoint> prices) {
        if (prices.isEmpty()) {
            return null;
        }

        PricePoint cheapest = prices.get(0);

        for (PricePoint price : prices) {
            if (price.price() < cheapest.price()) {
                cheapest = price;
            }
        }

        return cheapest;
    }

    // Dyraste timmen (vid lika pris: välj tidigast)
    public static PricePoint mostExpensivePrice(List<PricePoint> prices) {
        if (prices.isEmpty()) {
            return null; // Inget värde hittades
        }

        PricePoint mostExpensive = prices.get(0);

        for (PricePoint price : prices) {
            if (price.price() > mostExpensive.price()) {
                mostExpensive = price;
            }
        }

        return mostExpensive;
    }

    // Sliding Window: startindex för fönster med lägst totalsumma
    // Returnerar -1 om listan är för kort/ogiltig windowSize.
    public static int findBestWindowStart(List<PricePoint> prices, int windowSize) {
        if (prices == null || prices.size() < windowSize || windowSize <= 0) {
            return -1; // Inget värde hittades
        }

        // Första fönstret
        double currentSum = 0;
        for (int i = 0; i < windowSize; i++) {
            currentSum += prices.get(i).price();
        }

        double bestSum = currentSum;
        int bestStart = 0;

        // Sliding window fönstret
        for (int end = windowSize; end < prices.size(); end++) {
            // Ta bort första i gamla fönstret
            currentSum -= prices.get(end - windowSize).price();
            // Lägg till nya längst till höger
            currentSum += prices.get(end).price();

            int start = end - windowSize + 1;

            if (currentSum < bestSum) {
                bestSum = currentSum;
                bestStart = start;
            }
        }

        return bestStart;
    }

    // Summering av hittat fönster
    public static void printWindowSummary(List<PricePoint> prices, int start, int windowSize) {
        if (start < 0) {
            System.out.println("Ingen " + windowSize + "h-period (för få datapunkter).");
            return;
        }

        double sum = 0.0;
        for (int i = 0; i < windowSize; i++) {
            sum += prices.get(start + i).price();
        }
        double avg = sum / windowSize;

        PricePoint startPoint = prices.get(start);
        PricePoint endPoint   = prices.get(start + windowSize - 1);

        String startTime = PriceUtils.formatDateTime(startPoint); // använder datum+tid
        String endTime   = PriceUtils.formatDateTime(endPoint);

        System.out.println(windowSize + "h bästa fönster: "
                + startTime + " → " + endTime
                + "  (snitt " + PriceUtils.formatPrice(avg) + "kr/kWh)");
    }


    // Filtrera bort priser som redan passerat (börjar från kommande tider)
    public static List<PricePoint> filterFuturePrices(List<PricePoint> prices) {
        // Nuvarande tid svensk tidzon
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Europe/Stockholm"));

        List<PricePoint> futurePrices = new ArrayList<>();

        for (PricePoint p : prices) {
            OffsetDateTime priceTime = OffsetDateTime.parse(p.time());

            if (priceTime.isAfter(now)) {
                futurePrices.add(p);
            }
        }

        return futurePrices;
    }
}
