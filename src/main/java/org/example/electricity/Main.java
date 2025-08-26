/* The Real CLI entry, since App.java was only to test. This is my MAIN for this project */
package org.example.electricity;

// Imports and why I use them

import java.time.LocalDate; //DATE without time (YY-MM-DD)
import java.time.ZoneId; //Especially important and not to forget, converts to exact Time for relevant zone
import java.util.Locale; //Defines Language and Region, Used with DateTimeFormatter
import java.time.format.DateTimeFormatter; //Works together with Locale to display month/day in strings & right Language

public class Main {
    static void main(String[] args) {
        String zone = "SE3"; //Default value
        LocalDate date = LocalDate.now(ZoneId.of("Europe/Stockholm"));

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--zone" -> {
                    if (i + 1 < args.length) zone = args[++i].toUpperCase(Locale.ROOT);
                }
                case "--date" -> {
                    if (i + 1 < args.length) date = LocalDate.parse(args[++i], DateTimeFormatter.ISO_LOCAL_DATE);
                }
                case "--help" -> {
                    System.out.println("You typed" + "--help" + "THIS should provide user with help");
                    return;
                }
            }
        }

        //Fetch prices
        var prices = ElPriceCli.fetchDay(date, zone);
        if (prices.isEmpty()) {
            System.out.printf("No prices found for %s i %s.%n", date, zone);
            return;
        }

        final Locale SV = Locale.forLanguageTag("sv-SE"); //Swedish decimal, REPLACE this!!
        System.out.printf(SV, "fetched %d rows for %s in %s.%n", prices.size(), date, zone);
        var tz = ZoneId.of("Europe/Stockholm");
        int show = Math.min(24, prices.size());
        for (int i = 0; i < show; i++) {
            var p = prices.get(i);
            int h1 = p.timeStart().atZone(tz).getHour();
            int h2 = p.timeEnd().atZone(tz).getHour();
            System.out.printf(SV,"  %02d:00–%02d:00 → %.3f kr/kWh%n",
                    h1, h2 == 0 ? 24 : h2, p.sekPerkWh());
        }

    }
}
