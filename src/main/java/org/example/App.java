package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.text.DateFormatter;
import javax.swing.text.Element;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;


/// Create a CLI (Command-Line Interface) program that can:
/// Download prices for the current day and the next day (if available).
///  Print the mean price for the current 24-hour period.
///  Identify and print the hours with the cheapest and most expensive prices.
/// TODO: Fix algorithm for when tomorrows prices are also available
/// TODO: If multiple hours share the same price, select the earliest hour.
/// TODO: Determine the best time to charge an electric car for durations of 2, 4, or 8 hours. (Use a Sliding Window algorithm for this.)
/// TODO: Allow selection of the price zone ("zon") for which to retrieve data. (Possible input methods: command-line argument, config file, or interactive prompt.)
public class App {
    record ElectricityPrice(double SEK_per_kWh, double EUR_per_kWh, String EXR, String time_start, String time_end) {}
    record ElectricityInfo(int id, ElectricityPrice price) {}

    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        getPrices(date, time);

    }
    public static void getPrices(LocalDate date, LocalTime time){
        ArrayList<ElectricityPrice> electricityPrices = new ArrayList<>();
        String timeString = time.toString();
        String hourString = timeString.substring(0,2);
        int hour = Integer.parseInt(hourString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM-dd");
        String formattedDate = date.format(formatter);

        System.out.println(formattedDate);
        System.out.println(hour);
        if(hour < 13){
            electricityPrices = jsonDownloadAndConvert(formattedDate);
        }
        else{
            LocalDate nextDay = LocalDate.now().plusDays(1);
            String formattedNextDay = nextDay.format(formatter);
            electricityPrices = jsonDownloadAndConvert(formattedDate);
            ArrayList<ElectricityPrice> electricityPricesTomorrow = jsonDownloadAndConvert(formattedNextDay);
            electricityPrices.addAll(electricityPricesTomorrow);
        }
        calculateMean(electricityPrices, hour);
        findCheapestHour(electricityPrices);
        findMostExpensiveHour(electricityPrices);
    }

    private static void findMostExpensiveHour(ArrayList<ElectricityPrice> electricityPrices) {
        double mostExpensive = Integer.MIN_VALUE;
        String time = null;
        for(ElectricityPrice electricityPrice : electricityPrices) {
            if(electricityPrice.SEK_per_kWh > mostExpensive){
                mostExpensive = electricityPrice.SEK_per_kWh;
                time = electricityPrice.time_start;
            }
        }
        String hour= time.substring(11,13);
        int startHour = Integer.parseInt(hour);
        int endHour = startHour+1;
        System.out.println("The most expensive hour is between "+startHour+"  and "+endHour+". The price is "+mostExpensive+" SEK.");
    }

    private static void findCheapestHour(ArrayList<ElectricityPrice> electricityPrices) {
        double cheapest = Integer.MAX_VALUE;
        String time = null;
        for(ElectricityPrice electricityPrice : electricityPrices) {
            if(electricityPrice.SEK_per_kWh < cheapest){
                cheapest = electricityPrice.SEK_per_kWh;
                time = electricityPrice.time_start;
            }
        }
        String hour= time.substring(11,13);
        int startHour = Integer.parseInt(hour);
        int endHour = startHour+1;
        System.out.println("The cheapest hour is between "+startHour+"  and "+endHour+". The price is "+cheapest+" SEK.");
    }

    private static void calculateMean(ArrayList<ElectricityPrice> electricityPrices, int hour) {
        if(hour < 13){
            double totalPrice = 0;
            for(ElectricityPrice electricityPrice : electricityPrices){
                System.out.println(electricityPrice.SEK_per_kWh);
                totalPrice += electricityPrice.SEK_per_kWh;
            }
            double mean = totalPrice / electricityPrices.size();
            System.out.println("The mean price for the current 24h period is " + mean + " SEK");
        }
        else{
            //TODO: calculate the coming 24 h mean price
        }

    }

    private static ArrayList<ElectricityPrice> jsonDownloadAndConvert(String date) {
        String baseURI = "https://www.elprisetjustnu.se/api/v1/prices";
        String URL = baseURI + "/"+date+"_SE3.json";
        System.out.println("URL: " + URL);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL)).build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList <ElectricityPrice>electricity = objectMapper.readValue(json, new TypeReference<ArrayList<ElectricityPrice>>() {
            });
            return electricity;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }



}
