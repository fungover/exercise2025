package org.example;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


public class App {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ange elområde: SE1, SE2, SE3 eller SE4 ");
        String area = scanner.nextLine().toUpperCase();

        while (true) {
            System.out.println("-------------");
            System.out.println("  Elpriser " + area );
            System.out.println("-------------");
            System.out.println("Vad vill du se:");
            System.out.println("1. Ladda ner alla priser");
            System.out.println("2. Visa medelpris för idag");
            System.out.println("3. Visa billigaste och dyraste timmar");
            System.out.println("4. Hitta bästa tid att ladda elbil");
            System.out.println("5. Avsluta");

            System.out.print("Ditt val: ");
            String menuOption = scanner.nextLine();

            List<PriceEntry> todayPrices;
            List<PriceEntry> allPrices;

            LocalDate today = LocalDate.now();
            LocalDate tomorrow = today.plusDays(1);

            try {
                // today's prices
                todayPrices = new ArrayList<>(ElectricityPricesHttpClient.fetchElectricityPrices(
                        today.getYear(),
                        today.getMonthValue(),
                        today.getDayOfMonth(),
                        area
                ));
                allPrices = new ArrayList<>(todayPrices); // today's prices first to allPrices
            } catch (Exception e) {
                System.out.println("Fel vid hämtning av dagens priser: " + e.getMessage());
                return;
            }

            try {
                // tomorrow's prices
                allPrices.addAll(ElectricityPricesHttpClient.fetchElectricityPrices(
                        tomorrow.getYear(),
                        tomorrow.getMonthValue(),
                        tomorrow.getDayOfMonth(),
                        area
                ));
            } catch (Exception e) {
                System.out.println("Morgondagens priser finns inte tillgängliga, visar endast dagens priser.");
            }



            switch (menuOption) {
                case "1":
                    Prices.showAllPrices(allPrices);
                    break;

                case "2":
                    Prices.showAveragePrice(todayPrices);
                    break;

                case "3":
                    Prices.showMinAndMaxPrice(allPrices);
                    break;

                case "4":
                    System.out.print("Laddningstid? (2, 4 eller 8 timmar: ");
                    int hours = Integer.parseInt(scanner.nextLine());
                    Prices.findBestChargingTime(allPrices, hours);
                    break;

                case "5":
                    System.out.println("Avslutar programmet.");
                    return;

                default:
                    System.out.println("Ogiltigt val, försök igen.");
            }

            System.out.println();
        }
    }



}

