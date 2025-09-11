package org.example;

import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Denna klass innehåller "verktygsmetoder" för att räkna på elpriserna
// Alla metoder är statiska och kan anropas direkt utan att skapa ett objekt
public class PriceUtils {

    // Räkna ut medelpris från en lista av PricePoint
    public static double averagePrice(List<PricePoint> prices) {
        double sum = 0;
        for (PricePoint p : prices) {
            sum += p.getPrice();
        }
        return sum / prices.size();
    }

    // Hitta billigaste timmen i listan
    public static PricePoint cheapestPrice(List<PricePoint> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("prices must not be empty");
        }
        PricePoint cheapest = prices.get(0);
        for (PricePoint p : prices) {
            if (p.getPrice() < cheapest.getPrice()) {
                cheapest = p;
            }
        }
        return cheapest;
    }


    public static PricePoint mostExpensivePrice(List<PricePoint> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("prices must not be empty");
        }
        PricePoint mostExpensive = prices.get(0);
        for (PricePoint p : prices) {
            if (p.getPrice() > mostExpensive.getPrice()) {
                mostExpensive = p;
            }
        }
        return mostExpensive;
    }

    // Hitta var det bästa fönstret av timmar börjar
    // Exempel: bästa 2h-blocket eller bästa 4h-blocket
    public static int findBestWindowStart(List<PricePoint> prices, int windowSize) {
        if (prices == null || prices.size() < windowSize) {
            throw new IllegalArgumentException("not enough data for window of size " + windowSize);
        }

        double bestAvg = Double.MAX_VALUE;
        int bestIndex = 0;

        // Testa varje block av storlek windowSize och jämför medelvärden
        for (int i = 0; i <= prices.size() - windowSize; i++) {
            double sum = 0;
            for (int j = 0; j < windowSize; j++) {
                sum += prices.get(i + j).getPrice();
            }
            double avg = sum / windowSize;

            if (avg < bestAvg) {
                bestAvg = avg;
                bestIndex = i;
            }
        }
        return bestIndex;
    }

    // Skapar en text som sammanfattar det bästa fönstret.
    // T.ex. "Bästa 2h-blocket: 00:00–02:00 (0,75 kr/kWh)"
    public static String windowSummary(List<PricePoint> prices, int start, int windowSize) {
        if (prices == null || start < 0 || start + windowSize > prices.size()) {
            throw new IllegalArgumentException("Ogiltigt fönster");
        }

        // Räkna ut medelpriset för blocket
        double sum = 0;
        for (int i = 0; i < windowSize; i++) {
            sum += prices.get(start + i).getPrice();
        }
        double avg = sum / windowSize;

        // Startdatum och starttid = första timmen i blocket
        String date = prices.get(start).getDate();
        String startHour = prices.get(start).getHour();

        // Sluttid = sista timmen i blocket + 1h (för att visa som ett intervall)
        String lastHour = prices.get(start + windowSize - 1).getHour();
        LocalTime endTime = LocalTime.parse(lastHour).plusHours(1);
        String endHour = endTime.format(DateTimeFormatter.ofPattern("HH:mm"));


        return "Bästa " + windowSize + "h-blocket (" + date + "): "
                + startHour + "–" + endHour
                + " (" + String.format("%.2f", avg) + "kr/kWh)";
    }
}