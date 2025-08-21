package org.example;
import com.fasterxml.jackson.jr.ob.JSON;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            URL url = new URI("https://www.elprisetjustnu.se/api/v1/prices/2025/08-20_SE3.json").toURL();
            List<Object> result = JSON.std.listFrom(url);

            for (Object o : result) {
                Map<String, Object> map = (Map<String, Object>) o;
                System.out.println(map);
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
//
//⭐ Extra Credit
//Enable importing actual hourly consumption data from a CSV file and calculate the total cost.