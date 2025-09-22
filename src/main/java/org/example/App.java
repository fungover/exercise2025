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
            String a = args[i];
            if ("--zone".equals(a)) {
                if (i + 1 >= args.length) { System.err.println("Missing value for --zone"); return; }
                zone = args[++i];
            } else if ("--date".equals(a)) {
                if (i + 1 >= args.length) { System.err.println("Missing value for --date"); return; }
                dateStr = args[++i];
            } else if ("--tomorrow".equals(a)) {
                includeTomorrow = true;
            } else if ("-h".equals(a) || "--help".equals(a)) {
                System.out.println("Usage: --zone SE1|SE2|SE3|SE4 [--date YYYY-MM-DD] [--tomorrow]");
                return;
            } else {
                System.err.println("Unknown option: " + a);
                return;
            }
        }

        if (!zone.matches("SE[1-4]")) {
            System.err.println("Ogiltig zon: " + zone + " (tillåtna: SE1–SE4)");
            return;
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
                // i
            }
        }

        if (all.isEmpty()) {
            System.out.println("Inga priser tillgängliga för " + zone + " " + date);
            return;
        }

        List<Price> first24 = all.subList(0, Math.min(24, all.size()));

        System.out.println("Zon: " + zone + " | Datum: " + date);
        System.out.println("Första: " + first24.get(0));
        System.out.println("Sista:  " + first24.get(first24.size() - 1));
        System.out.println("Medelpris: " + PriceStats.mean(first24));
        System.out.println("Billigaste: " + PriceStats.cheapest(first24));
        System.out.println("Dyraste:    " + PriceStats.mostExpensive(first24));
        for (int h : new int[]{2, 4, 8}) {
            if (first24.size() >= h) {
                PriceStats.bestWindow(first24, h);
            }
        }

    }
}
