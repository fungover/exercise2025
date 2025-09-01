package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.time.LocalDate;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class electriCheck {

    static Scanner scanner = new Scanner(System.in);
    public static String zone = "";
    public static String date = formatedDate();

    static void main() {

        System.out.println("Welcome to ElectriCheck \n This is a simple electricity price checking software. \n This program will download the electricity prices for the upcoming 24 hours \n (note that upcoming prices are released after 1 PM so checking before that will not take the full 24 hours into account) \n");
        System.out.println("Please input your price zone? \n \n SE1 = Luleå / Norra Sverige \n SE2 = Sundsvall / Norra Mellansverige \n SE3 = Stockholm / Södra Mellansverige \n SE4 = Malmö / Södra Sverige \n");

        do {
            System.out.print("Zone: ");
            zone = scanner.nextLine();
            zone = zone.toUpperCase();

            if (!zone.equalsIgnoreCase("SE1") && !zone.equalsIgnoreCase("SE2") && !zone.equalsIgnoreCase("SE3") && !zone.equalsIgnoreCase("SE4")) {
                System.out.println("Invalid input. Please try again");
            }
        } while (!zone.equalsIgnoreCase("SE1") && !zone.equalsIgnoreCase("SE2") && !zone.equalsIgnoreCase("SE3") && !zone.equalsIgnoreCase("SE4"));

        System.out.println("Loading current Electricity prices... \n");

        ArrayList<priceObject> prices = getPrices(zone, date);
        double medianPrice = getMedianPrice(prices);
        ArrayList<priceObject> lowHighPrices = getLowHighPrices(prices);

        System.out.println("The time and price of the cheapest hours:\n");
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter apiDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        int counter = 0;
        for (int i = 0; i <= 2; i++) {
            priceObject currentObject = lowHighPrices.get(i);

            LocalDateTime startTime = LocalDateTime.parse(currentObject.time_start, apiDateTimeFormatter);
            LocalDateTime endTime = LocalDateTime.parse(currentObject.time_end, apiDateTimeFormatter);

            counter++;
            String formatedStartTime = startTime.format(time);
            String formatedEndTime = endTime.format(time);
            System.out.println(counter + ".Time: " + formatedStartTime + " - " + formatedEndTime + " Price: " + currentObject.SEK_per_kWh + " SEK");
        }
        counter = 0;
        System.out.println("\nThe time and price of the most expensive hours: \n");
        for (int i = (lowHighPrices.size() - 1); i >= (lowHighPrices.size() - 3); i--) {
            priceObject currentObject = lowHighPrices.get(i);

            LocalDateTime startTime = LocalDateTime.parse(currentObject.time_start, apiDateTimeFormatter);
            LocalDateTime endTime = LocalDateTime.parse(currentObject.time_end, apiDateTimeFormatter);

            counter++;
            String formatedStartTime = startTime.format(time);
            String formatedEndTime = endTime.format(time);
            System.out.println(counter + ".Time: " + formatedStartTime + " - " + formatedEndTime + " Price: " + currentObject.SEK_per_kWh + " SEK");
        }


        System.out.println("\nAverage price for 24 hour period: " + medianPrice + " SEK");

    }

    public static String formatedDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter monthDay = DateTimeFormatter.ofPattern("MM-dd");
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
        return year.format(now) + "/" + now.format(monthDay);
    }

    static ArrayList<priceObject> getPrices(String zone, String date) {
        String urlWithParam = "https://www.elprisetjustnu.se/api/v1/prices/" + date + "_" + zone + ".json";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithParam))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
        ArrayList<priceObject> pricesList = null;
        if (response != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                pricesList = mapper.readValue(response.body(), new TypeReference<ArrayList<priceObject>>() {
                });
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Error: response is null");
        }
        if (pricesList != null) {
            return pricesList;
        } else {
            return new ArrayList<>();
        }
    }

    //Egen notis för framtiden. Ha fälten privata och lär dig om getters och setters!
    public static class priceObject {
        public double SEK_per_kWh;
        public double EUR_per_kWh;
        public double EXR;
        public String time_start;
        public String time_end;
    }

    public static double getMedianPrice(ArrayList<priceObject> prices) {
        double medianPrice = 0;
        for (priceObject price : prices) {
            double eachPrice = price.SEK_per_kWh;
            medianPrice += eachPrice;
        }
        medianPrice /= prices.size();
        return medianPrice;
    }

    public static ArrayList<priceObject> getLowHighPrices(ArrayList<priceObject> prices) {
        ArrayList<priceObject> lowHighPrices = new ArrayList<>();
        prices.sort((obj1, obj2) -> Double.compare(obj1.SEK_per_kWh, obj2.SEK_per_kWh));
        for (int i = 0; i <= 2; i++) {
            lowHighPrices.add(prices.get(i));
        }
        for (int i = (prices.size() - 3); i <= (prices.size() - 1); i++) {
            lowHighPrices.add(prices.get(i));
        }
        return lowHighPrices;
    }

}