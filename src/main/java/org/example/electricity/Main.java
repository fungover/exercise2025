/* The Real CLI entry, since App.java was only to test. This is my MAIN for this project */
package org.example.electricity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public class Main {
   public static void main(String[] args) {
        String zone = "SE3"; //Default value
        LocalDate date = LocalDate.now(ZoneId.of("Europe/Stockholm"));

        /* Reading CLI arguments */
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--zone" -> {
                    if (i + 1 < args.length) zone = args[++i].toUpperCase(Locale.ROOT);
                }
                case "--date" -> {
                    if (i + 1 < args.length) date = LocalDate.parse(args[++i], DateTimeFormatter.ISO_LOCAL_DATE);
                }
                case "--help" -> {
                    Printer.printHelp();
                    return;
                }
            }
        }
        /* Fetch prices */
        var prices = ElPriceCli.fetchDay(date, zone);
        if (prices.isEmpty()) {
            Printer.printError("No prices found for" + date + " i " + zone);
            return;
        }

        {
            var w = Stats.bestWindow(prices, 2);
            int end = w.startHour() + w.length();
            System.out.printf("Best Price %dh: %02d:00–%02d:00  sum=%.2f  Average=%.2f%n", 2, w.startHour(), end, w.sum(), w.average());
        }
        {
            var w = Stats.bestWindow(prices, 4);
            int end = w.startHour() + w.length();
            System.out.printf("Best Price %dh: %02d:00–%02d:00  sum=%.2f  Average=%.2f%n", 4, w.startHour(), end, w.sum(), w.average());
        }
        {
            var w = Stats.bestWindow(prices, 8);
            int end = w.startHour() + w.length();
            System.out.printf("Best Price %dh: %02d:00–%02d:00  sum=%.2f  Average=%.2f%n", 8, w.startHour(), end, w.sum(), w.average());
        }

        /* Using my Printer */
        Printer.printHeader(date, zone, prices.size());
        Printer.printPrices(prices);
        Printer.printStats(prices);

    }

}

