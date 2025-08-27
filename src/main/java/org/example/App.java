package org.example;

import java.util.Scanner;

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

        while (true) {
            System.out.println("-------------");
            System.out.println("  Elpriser");
            System.out.println("-------------");
            System.out.println("Vad vill du se:");
            System.out.println("1. Ladda ner alla priser");
            System.out.println("2. Visa medelpris för idag");
            System.out.println("3. Visa billigaste och dyraste timmar");
            System.out.println("4. Hitta bästa tid att ladda elbil");
            System.out.println("5. Avsluta");

            System.out.print("Ditt val: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Valde: Ladda ner priser");

                    break;

                case "2":
                    System.out.println("Valde: Visa medelpris för idag");
                    break;

                case "3":
                    System.out.println("Valde: Visa billigaste och dyraste timmar");
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

    // Placeholders
    private static void allPrices() {
        System.out.println("Du valde: Ladda ner alla priser");
    }

    private static void meanPrice() {
        System.out.println("Du valde: Visa medelpris för idag");
    }

    private static void minMax() {
        System.out.println("Du valde: Visa billigaste och dyraste timmar");
    }

    private static void bestChargingTime() {
        System.out.println("Du valde: Hitta bästa tid att ladda elbil");

    }
}

