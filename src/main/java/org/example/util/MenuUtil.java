package org.example.util;

import org.example.service.ApiClient;
import org.example.service.PriceService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MenuUtil {
    public static String getArea(Scanner scanner) {
        int area;
        System.out.println("Choose area:");

        do {
            System.out.println("1. Luleå / Northern Sweden");
            System.out.println("2. Sundsvall / Northern Central Sweden");
            System.out.println("3. Stockholm / Southern Central Sweden");
            System.out.println("4. Malmö / Southern Sweden");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                area = scanner.nextInt();
                if (area < 1 || area > 4) {
                    System.out.println("Invalid choice, must be between 1 and 4");
                }
            } else {
                System.out.println("Invalid choice, must be a number between 1 and 4");
                scanner.next();
                area = -1;
            }
        } while (area < 1 || area > 4);

        return "SE" + area;
    }

    public static int getChoice(Scanner scanner) {
        int choice;

        do {
            System.out.println("1. Download prices for the current day and next day");
            System.out.println("2. Print the mean price for the current 24-hour period");
            System.out.println("3. Identify and print the hours with the cheapest and most expensive prices");
            System.out.println("4. Determine the best time to charge an electric car for durations of 2, 4, or 8 hours");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice < 1 || choice > 5) {
                    System.out.println("Invalid choice, must be between 1 and 5");
                }
            } else {
                System.out.println("Invalid choice, must be a number between 1 and 5");
                scanner.next();
                choice = -1;
            }
        } while (choice < 1 || choice > 5);

        return choice;
    }

    public static String handleChoice(int choice, String area) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        String date = today.format(formatter);
        String dateTomorrow = tomorrow.format(formatter);
        String urlToday = ApiClient.urlBuilder( date, area);
        String urlTomorrow = ApiClient.urlBuilder( dateTomorrow, area);

        switch (choice) {
            case 1:
                System.out.println("Downloading prices for the current day and next day");
                //Today's prices
                PriceService.downloadPrices(urlToday, "TodaysPrices.csv", "Today's prices");

                //Tomorrow's prices'
                PriceService.downloadPrices(urlTomorrow, "TomorrowsPrices.csv", "Tomorrow's prices");

                break;

            case 2:
                System.out.println("Printing the mean price for the current 24-hour period");
                PriceService.fetchAndCalculateMeanPrice(urlToday, "MeanPrices.csv", "Today's mean price");
                break;

            case 3:
                System.out.println("Identifying and printing the hours with the cheapest and most expensive prices");
                PriceService.fetchAndPrintCheapestAndMostExpensivePrices(urlToday, "CheapestAndExpensivePrices.csv", "Today's Cheapest and Most Expensive");
                break;

            case 4:
                System.out.println("Determining the best time to charge an electric car for durations of 2, 4, or 8 hours");
                PriceService.optimalChargeTime(urlToday, "OptimalChargingToday.csv", "Today's optimal charge time");
                PriceService.optimalChargeTime(urlTomorrow, "OptimalChargingTomorrow.csv", "Tomorrow's optimal charge time");
                break;

            case 5:
                System.out.println("Exiting");
                break;
        }

        return null;
    }
}
