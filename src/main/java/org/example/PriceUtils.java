package org.example;

import java.util.List;

// Denna klass innehåller "verktygsmetoder" för att räkna på elpriserna
// Alla metoder är statiska och kan anropas direkt utan att skapa ett objekt
public class PriceUtils {

    // Räkna ut medelpris från en lista av PricePoint
    public static double averagePrice(List<PricePoint> prices) {
        if (prices == null || prices.isEmpty()) {
            throw new IllegalArgumentException("prices must not be empty");
        }
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
        // Validera input: fönsterstorlek måste vara positiv
        if (windowSize <= 0) {
            throw new IllegalArgumentException("windowSize måste vara > 0");
        }
        if (prices == null || prices.size() < windowSize) {
            throw new IllegalArgumentException("för få datapunkter för fönster av storlek " + windowSize);
        }

        // Initiera första summan för fönstret [0, windowSize)
        double sum = 0;
        for (int j = 0; j < windowSize; j++) {
            sum += prices.get(j).getPrice();
        }
        double bestAvg = sum / windowSize;
        int bestIndex = 0;

        // Skjut fönstret framåt i listan, uppdatera summan i O(1) per steg
        for (int i = 1; i <= prices.size() - windowSize; i++) {
            // Ta bort det äldsta värdet och lägg till det nya
            sum += prices.get(i + windowSize - 1).getPrice();
            sum -= prices.get(i - 1).getPrice();

            double avg = sum / windowSize;

            // Spara om vi hittat ett bättre (lägre) medelvärde
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
        // Validera: måste finnas data och ett positivt fönster
        if (prices == null || windowSize <= 0 || start < 0 || start + windowSize > prices.size()) {
            throw new IllegalArgumentException("Ogiltigt fönster");
        }

        // Datumintervall (hanterar midnatt)
        String date = prices.get(start).getDate();
        String endDate = prices.get(start + windowSize - 1).getDate();
        String dateSpan = endDate.equals(date) ? date : (date + "–" + endDate);

        // Starttid = timmen på första punkten
        String startHour = prices.get(start).getHour();

        // Sluttid = sista timmen + 1h (så att "23:00,00:00,01:00" → "23:00–02:00")
        var lastTime = java.time.LocalTime.parse(
                prices.get(start + windowSize - 1).getHour(),
                java.time.format.DateTimeFormatter.ofPattern("HH:mm")
        );
        String endHour = lastTime.plusHours(1)
                .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));

        // Räkna ut medelpris
        double sum = 0;
        for (int i = start; i < start + windowSize; i++) {
            sum += prices.get(i).getPrice();
        }
        double avg = sum / windowSize;

        // Formatera med svenskt decimalkomma
        String formattedAvg = String.format(java.util.Locale.forLanguageTag("sv-SE"), "%.2f", avg);

        // Bygg upp resultattexten
        return "Bästa " + windowSize + "h-blocket (" + dateSpan + "): "
                + startHour + "–" + endHour
                + " (" + formattedAvg + "kr/kWh)";
    }

}