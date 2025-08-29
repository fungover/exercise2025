package org.example;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final ZoneId TZ = ZoneId.of("Europe/Stockholm");
    private static final DateTimeFormatter MD = DateTimeFormatter.ofPattern("MM-dd");

    public static void main(String[] args) throws Exception {
        String zone = "SE3";
        String dateStr = null;
        boolean includeTomorrow = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--zone")) zone = args[i + 1];
            if (args[i].equals("--date")) dateStr = args[i + 1];
            if (args[i].equals("--tomorrow")) includeTomorrow = true;
        }

        LocalDate date = (dateStr == null) ? LocalDate.now(TZ) : LocalDate.parse(dateStr);

        ElprisApiClient api = new ElprisApiClient();
        ElprisParser parser = new ElprisParser();

        List<Price> all = new ArrayList<>();
        String jsonToday = api.fetchRaw(date.getYear(), MD.format(date), zone);
        all.addAll(parser.parsePrices(jsonToday));

        if (includeTomorrow) {
            LocalDate next = date.plusDays(1);
            try {
                String jsonTomorrow = api.fetchRaw(next.getYear(), MD.format(next), zone);
                all.addAll(parser.parsePrices(jsonTomorrow));
            } catch (Exception e) {
                // ignorera om inget finns
            }
        }

        List<Price> first24 = all.subList(0, Math.min(24, all.size()));

        System.out.println("Zon: " + zone + " | Datum: " + date);
        System.out.println("Första: " + first24.get(0));
        System.out.println("Sista:  " + first24.get(first24.size() - 1));
        System.out.println("Medelpris: " + PriceStats.mean(first24));
        System.out.println("Billigaste: " + PriceStats.cheapest(first24));
        System.out.println("Dyraste:    " + PriceStats.mostExpensive(first24));
        PriceStats.bestWindow(first24, 2);
        PriceStats.bestWindow(first24, 4);
        PriceStats.bestWindow(first24, 8);
    }
}
