package org.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.io.IOException;

public class App {
    static void main() {
        String zone = zoneSelection();

        System.out.println("Selected zone: " + zone);

        DateTimeFormatter dayFmt = DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter monthFmt = DateTimeFormatter.ofPattern("MM");

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        API api = new API();

        try {
            List<ElectricityPrice> pricesToday = api.fetchPrices(zone, dayFmt.format(today), monthFmt.format(today), String.valueOf(today.getYear()));
            List<ElectricityPrice> pricesTomorrow = api.fetchPrices(zone, dayFmt.format(tomorrow), monthFmt.format(tomorrow), String.valueOf(tomorrow.getYear()));

            double meanPrice = CalculateMeanPrice(pricesToday);
            double meanPriceTomorrow = CalculateMeanPrice(pricesTomorrow);

            System.out.println("Mean price on the electricity for today: " + meanPrice + "(SEK/kWh)\nMean price on the electricity for tomorrow: " + meanPriceTomorrow + "(SEK/kWh)");

        } catch (IOException | InterruptedException e) {
            System.out.println("Error fetching prices: " + e.getMessage());
        }
    }

    public static String zoneSelection() {
        System.out.println("Welcome! Please select what zone you would like to see the electrical prices in.");
        System.out.println("(1) - Luleå / Norra Sverige");
        System.out.println("(2) - Sundsvall / Norra Mellansverige");
        System.out.println("(3) - Stockholm / Södra Mellansverige");
        System.out.println("(4) - Malmö / Södra Sverige");
        System.out.println("Please select what zone you would like to see with the corresponding number");

        Scanner scanner = new Scanner(System.in);
        String selection = scanner.nextLine();

        return "SE" + selection;
    }

    public static double CalculateMeanPrice(List<ElectricityPrice> prices) {
        double meanP = 0;

        for (ElectricityPrice price : prices) {
            meanP += price.SEK_per_kWh;
        }

        meanP /= prices.size();

        return meanP;
    }
}
