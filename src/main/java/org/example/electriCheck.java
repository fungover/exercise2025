package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

public class electriCheck {

    static Scanner scanner = new Scanner(System.in);
    public static String zone = "";
    public static String date = formatedDate();


    public static void main(String[] args) {

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

        System.out.println("Loading current Electricity prices...");
        ArrayList<priceObject> prices = getPrices(zone, date);
        System.out.println("sucess:" + prices);
        System.out.println(date + " " + zone);
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

}