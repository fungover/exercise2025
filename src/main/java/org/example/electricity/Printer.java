/* This class is for all formatting and printing */
package org.example.electricity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

public class Printer {
    public static final ZoneId TZ = ZoneId.of("Europe/Stockholm");
    public static final Locale SV = Locale.forLanguageTag("sv-SE");

    /* Header with print "Fetched 24 rows for yyyy-mm-dd in your zone" */
    public static void printHeader(LocalDate date, String zone, int count) {
        System.out.printf(SV, "Fetched %d Hours for %s in %s %n", count, date, zone);
    }

    /* List with prices */
    public static void printPrices(List<PriceData> prices) {
        if (prices == null || prices.isEmpty()) {
            System.out.println("No Electricity Prices to show");
            return;
        }
        int show = Math.min(24, prices.size());
        for (int i = 0; i < show; i++) {
            var p = prices.get(i);
            int h1 = p.timeStart().atZone(TZ).getHour();
            int h2 = p.timeEnd().atZone(TZ).getHour();
            System.out.printf(SV, " %02d:00–%02d:00 → %.3f kr/kWh%n", h1, h2 == 0 ? 24 : h2, p.sekPerkWh());
        }
    }

    public static void printStats(List<PriceData> prices) {
        if (prices == null || prices.isEmpty()) {
            System.out.println("No statistics to show. List is empty");
            return;
        }
        double avg = Stats.avg(prices);
        var min = Stats.min(prices);
        var max = Stats.max(prices);
        System.out.printf(SV, "Average Price is: %.3f kr/kWh%n", avg);

        if (min != null) {
            int h = min.timeStart().atZone(TZ).getHour();
            System.out.printf(SV, "Lowest Price is: %.3f kr/kWh kl %02d%n", min.sekPerkWh(), h);
        }

        if (max != null) {
            int h = max.timeStart().atZone(TZ).getHour();
            System.out.printf(SV, "Highest Price is: %.3f kr/kWh kl %02d%n", max.sekPerkWh(), h);
        }
    }

    public static void printError(String message) {
        System.err.println("Error" + message);
    }

    /* Manual for how to use this program */
    public static void printHelp() {
        System.out.println("""
            Usage: java -jar elPrice.jar [--zone SE1|SE2|SE3|SE4] [--date YYYY-MM-DD]
              --zone   Zone (standard: SE3)
              --date   Date (standard: today in Europe/Stockholm)
              --help   Show this help
            """);
    }

    private Printer() {

    }


}
