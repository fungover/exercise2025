package org.example;
import com.fasterxml.jackson.jr.ob.JSON;
import org.example.model.MeanPrice;
import org.example.util.PriceUtil;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM-dd");

        // LocalDateTime now = LocalDateTime.of(2025, 8,22, 13, 1); // To simulate time past 13.00 for testing purposes
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = now.plusDays(1); // Set -1 for testing tomorrowResults

        String todayPath = now.format(formatter);
        String tomorrowPath = tomorrow.format(formatter);
        String region = "SE4";

        URL urlToday = new URL("https://www.elprisetjustnu.se/api/v1/prices/" + todayPath + "_" + region + ".json");
        URL urlTomorrow = new URL("https://www.elprisetjustnu.se/api/v1/prices/" + tomorrowPath + "_" + region + ".json");

        try {
            List<Object> todayResults = JSON.std.listFrom(urlToday);

            for (Object o : todayResults) {
                Map<String, Object> map = (Map<String, Object>) o;
                System.out.println(map);
            }

            MeanPrice todayMean = PriceUtil.calculateMeanPrice(urlToday);
            System.out.println("Mean price for today: " + todayMean);

            // Condition to ensure the next days data is available before processing it
            if (now.getHour() >= 13) {
                List<Object> tomorrowResults = JSON.std.listFrom(urlTomorrow);
                System.out.println("Prices for tomorrow:");
                for (Object o : tomorrowResults) {
                    Map<String, Object> map = (Map<String, Object>) o;
                    System.out.println(map);
                }
                MeanPrice tomorrowMean = PriceUtil.calculateMeanPrice(urlTomorrow);
                System.out.println("Mean price for tomorrow: " + tomorrowMean);
            } else {
                System.out.println("Prices for tomorrow are not available yet.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

//##ASSIGNMENT:

//Electricity prices vary hour to hour, and sometimes the difference can be quite substantial.
// To help us optimize when to turn on the sauna or charge electric cars, we want a small CLI program that provides the necessary information.
//
//We can retrieve electricity prices—both historical and for the coming 24 hours—from the Elpris API.
//
//✅ Requirements
//Create a CLI (Command-Line Interface) program that can:
//
//Download prices for the current day and the next day (if available). X
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
//
//⭐ Extra Credit
//Enable importing actual hourly consumption data from a CSV file and calculate the total cost.