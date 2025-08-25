package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Electricity Price Tool Menu ===");

        Scanner scanner = new Scanner(System.in);
        String area = getArea(scanner);
        int choice = getChoice(scanner);

        handleChoice(choice, area);

        /* String URL = "https://www.elprisetjustnu.se/api/v1/prices/2025/08-19_SE3.json";
        getPrices(URL); */
    }

    private static String getArea(Scanner scanner) {
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

    private static int getChoice(Scanner scanner) {
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

    private static String handleChoice(int choice, String area) {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        String date = today.format(formatter);
        String dateTomorrow = tomorrow.format(formatter);
        String urlToday = urlBuilder( date, area);
        String urlTomorrow = urlBuilder( dateTomorrow, area);

        switch (choice) {
            case 1:
                System.out.println("Downloading prices for the current day and next day");
                //Today's prices
                downloadAndSavePrices(urlToday, "prices.txt", "Today's prices");

                //Tomorrow's prices'
                downloadAndSavePrices(urlTomorrow, "pricesTomorrow.txt", "Tomorrow's prices");

                break;
            case 2:
                System.out.println("Printing the mean price for the current 24-hour period");
                fetchAndCalculateMeanPrice(urlToday, "prices.txt", "Today's mean price");
                break;
            case 3:
                System.out.println("Identifying and printing the hours with the cheapest and most expensive prices");
                break;
            case 4:
                System.out.println("Determining the best time to charge an electric car for durations of 2, 4, or 8 hours");
                break;
            case 5:
                System.out.println("Exiting");
                break;
        }

        return null;
    }

    private static String urlBuilder(String date, String zone) {
        String year = String.valueOf(java.time.LocalDate.now().getYear());
        return "https://www.elprisetjustnu.se/api/v1/prices/" + year + "/" + date + "_" + zone + ".json";
    }

    private static HttpResponse<String> getPrices(String url_string) {
        HttpResponse<String> response = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url_string))
                    .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private static void createFile(String fileName) {
        try {
           File myObj = new File("prices.txt");
           if (myObj.createNewFile()) {
               System.out.println("File created: " + myObj.getName());
           } else {
               System.out.println("File already exists.");
           }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void writeToFile(String fileName, String content) {
        try {
            FileWriter myWriter = new FileWriter(fileName, true);
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {}
    }

    public record PriceEntry(
            double SEK_per_kWh,
            double EUR_per_kWh,
            double EXR,
            OffsetDateTime time_start,
            OffsetDateTime time_end) {}

    private static void downloadAndSavePrices(String url, String fileName, String dayLabel) {
        HttpResponse<String> prices = getPrices(url);
        if (prices != null && prices.statusCode() == 200) {
            System.out.println(dayLabel);

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            try {
                PriceEntry[] entries = mapper.readValue(prices.body(), PriceEntry[].class);
                System.out.println(dayLabel + " prices:");
                createFile(fileName);

                boolean firstLine = true;

                for (PriceEntry entry : entries) {
                    if (firstLine) {
                        System.out.println("===== " + dayLabel + " =====");
                        writeToFile(fileName, "===== " + dayLabel + " =====\n");
                        firstLine = false;
                    }
                    System.out.println(entry.time_start + " - " + entry.time_end + ": " + entry.SEK_per_kWh);
                    writeToFile(fileName, entry.time_start + " - " + entry.time_end + ": " + entry.SEK_per_kWh + " SEK_per_kWh" + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println(dayLabel + "'s prices could not be downloaded. Status: " +
                    (prices != null ? prices.statusCode() : "No response"));
        }
    }

    private static void fetchAndCalculateMeanPrice(String url, String fileName, String dayLabel) {
        HttpResponse<String> prices = getPrices(url);
        if (prices != null && prices.statusCode() == 200) {
            System.out.println(dayLabel);

            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            boolean firstLine = true;

            try {
                PriceEntry[] entries = mapper.readValue(prices.body(), PriceEntry[].class);
                int numberOfEntries = entries.length;
                double totalPrice = 0;
                for (PriceEntry entry : entries) {
                    totalPrice += entry.SEK_per_kWh;
                }
                double meanPrice = totalPrice / numberOfEntries;

                System.out.println(dayLabel + " prices:");
                createFile(fileName);

                if (firstLine) {
                    System.out.println(dayLabel + "'s prices:");
                    writeToFile(fileName, "===== " + dayLabel + " =====\n");
                    firstLine = false;
                }

                System.out.println(meanPrice + " SEK_per_kWh");
                writeToFile(fileName, meanPrice + " SEK_per_kWh" + "\n");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(dayLabel + "'s prices could not be downloaded. Status: " +
                    (prices != null ? prices.statusCode() : "No response"));
        }
    }
}