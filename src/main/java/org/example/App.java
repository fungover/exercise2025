package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException {

        // TODO GET DATE/TIME
        LocalDateTime localDate = LocalDateTime.now();

        int getYear = localDate.getYear();
        int getMonth = localDate.getMonthValue();
        int getDay = localDate.getDayOfMonth();
        int getHour = localDate.getHour();
        int getMinute = localDate.getMinute();

        boolean infoDebug = false;

        int testDouble = 99;
        int testOne = 9;

        String sMonth = String.format("%02d", getMonth);
        String sDay = String.format("%02d", getDay);
        String sHour = String.format("%02d", getHour);
        String sMinute = String.format("%02d", getMinute);

        String sComplete = String.format("Year: %d | Month: %s | Day: %s | Hour: %s",getYear,sMonth, sDay, sHour);

        System.out.println(sComplete);

        if (infoDebug) {

            System.out.println("Raw time format:" + localDate);

            System.out.println("Month: " + sMonth);
            System.out.println("Day: " + sDay);
            System.out.println("Hour: " + sHour);
            System.out.println("Minute: " + sMinute);

            System.out.println("Year: " + getYear);
            System.out.println("Month " + getMonth);
            System.out.println("Day " + getDay);
            System.out.println("Hour " + getHour);
            System.out.println("Minute " + getMinute);

            //System.out.println(Klass1);
            //System.out.println(Klass2);
            //System.out.println(Klass3);
            //System.out.println(Klass4);
        }


        // TODO Priceclasses
        String k1 = "SE1";
        String k2 = "SE2";
        String k3 = "SE3";
        String k4 = "SE4";

        // Luleå/ Norra Sverige
        String Klass1 = String.format("Luleå / Norra Sverige: %s", k1);
        // Sundsvall/ Norra Mellansverige
        String Klass2 = String.format("Sundsvall / Norra Mellansverige: %s", k2);
        // Stockholm/ Södra Mellansverige
        String Klass3 = String.format("Stockholm / Södra Mellansverige: %s", k3);
        // Malmö/ Södra Sverige
        String Klass4 = String.format("Malmö / Södra Sverige: %s", k4);

        // TODO MENU
        System.out.println("------------------------------------");
        System.out.println("Enter Region code [1-4]");
        System.out.println("------------------------------------");
        System.out.println("[1] Luleå / Norra Sverige");
        System.out.println("[2] Sundsvall / Norra Mellansverige");
        System.out.println("[3] Stockholm / Södra Mellansverige");
        System.out.println("[4] Malmö / Södra Sverige");
        System.out.println("------------------------------------");

        System.out.print("Enter Price Class: ");


        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        String priceClass = null;


        if (input.isEmpty()) {
            System.out.println("No Price Class entered. Exiting now.");
            return;
        }

        switch (input) {
            case "1" -> priceClass = "SE1";
            case "2" -> priceClass = "SE2";
            case "3" -> priceClass = "SE3";
            case "4" -> priceClass = "SE4";
            default -> {
                System.out.println("Please enter a valid Price Class and try again.");
                return;
            }
        }

          //  System.out.println("");
            //System.out.println("You chose " + priceClass);

            String url = String.format("https://www.elprisetjustnu.se/api/v1/prices/%d/%s-%s_%s.json", getYear, sMonth, sDay, priceClass);

            //TODO HTTP
            try (HttpClient client = HttpClient.newHttpClient()) {


                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .timeout(Duration.ofSeconds(30))
                        .header("Accept", "application/json")
                        .build();

                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    //System.out.println("Status code" + response.statusCode());
                    //System.out.println("Response body: " + response.body());

                    String json = response.body();
                    ObjectMapper mapper = new ObjectMapper();

                    List<Price> prices = mapper.readValue(json, new TypeReference<List<Price>>() {});

                    int responseNumber = 0;
                    double total = 0;
                    //TODO INFO OUTPUT
                    for  (Price price : prices) {
                        OffsetDateTime timeStart = OffsetDateTime.parse(price.time_start);
                        OffsetDateTime timeEnd = OffsetDateTime.parse(price.time_end);

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");

                        //System.out.println("-------------");
                        System.out.println("Object nr: " + responseNumber );
                        System.out.println("Start: " + timeStart.format(formatter));
                        System.out.println("End: " + timeEnd.format(formatter));
                        System.out.println("Price (SEK): " + price.SEK_per_kWh);
                        System.out.println("Price (EUR): " + price.EUR_per_kWh);
                        System.out.println("-------------");
                        responseNumber++;



                        total += price.SEK_per_kWh;

                        System.out.println("Total Price: " + total);

                    }
                    double meanPrice = total / responseNumber;
                    System.out.println("Mean Price: " + meanPrice);



                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        //TODO PRICE CLASSES
    public static class Price{
        public String time_start;
        public String time_end;
        public double SEK_per_kWh;
        public double EUR_per_kWh;
        public double EXR;
    }
    }
