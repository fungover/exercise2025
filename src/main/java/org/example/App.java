package org.example;

import java.util.Scanner;
import static org.example.Prices.showAllPrices;


import com.google.gson.JsonObject;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;



//Electricity prices vary hour to hour, and sometimes the difference can be quite substantial. To help us optimize when to turn on the sauna or charge electric cars, we want a small CLI program that provides the necessary information.
//
//We can retrieve electricity prices—both historical and for the coming 24 hours—from the Elpris API.
//
//✅ Requirements
//Create a CLI (Command-Line Interface) program that can:
//
//Download prices for the current day and the next day (if available).
//
//Print the mean price for the current 24-hour period.
//
//Identify and print the hours with the cheapest and most expensive prices.
//
//If multiple hours share the same price, select the earliest hour.
//
//Determine the best time to charge an electric car for durations of 2, 4, or 8 hours. (Use a Sliding Window algorithm for this.)
//
//Allow selection of the price zone ("zon") for which to retrieve data. (Possible input methods: command-line argument, config file, or interactive prompt.)

public class App {
    public static void main(String[] args) {
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
            String choice = scanner.nextLine();

            List<PriceEntry> prices = null;

            try {
                prices = ElectricityPricesHttpClient.fetchElectricityPrices(
                        LocalDate.now().getYear(),
                        LocalDate.now().getMonthValue(),
                        LocalDate.now().getDayOfMonth(),
                        area
                );
            } catch (Exception e) {
                System.out.println("Fel vid hämtning av priser: " + e.getMessage());
                return;
            }

            switch (choice) {
                case "1":
                    Prices.showAllPrices(prices);

                    break;

                case "2":
                    Prices.showAveragePrice(prices);
                    break;

                case "3":
                    Prices.showMinAndMaxPrice(prices);
                    break;

                case "4":
                    System.out.println("Valde: Hitta bästa tid att ladda elbil");
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

