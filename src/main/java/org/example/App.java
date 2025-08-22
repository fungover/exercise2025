package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.ElprisApi;
import org.example.cli.MenuOptions;
import org.example.cli.ZonePicker;
import org.example.util.ElectricityPrice;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/*

TODO 1.Create a CLI (Command-Line Interface) program that can:

TODO done 2.Download prices for the current day and the next day (if available).

TODO done 3.Print the mean price for the current 24-hour period.

TODO 4.Identify and print the hours with the cheapest and most expensive prices.

TODO 5.If multiple hours share the same price, select the earliest hour.

TODO 6.Determine the best time to charge an electric car for durations of 2, 4, or 8 hours.
(Use a Sliding Window algorithm for this.)

done 7.Allow selection of the price zone ("zon") for which to retrieve data.
(Possible input methods: command-line argument, config file, or interactive prompt.)


 */

public class App {
    ZonePicker zonePicker = new ZonePicker();

    public static void main(String[] args) throws Exception {
        ElprisApi api = new ElprisApi();
        String zone = new ZonePicker().getZone();
        String today = api.getRequest(LocalDate.now(), zone);
        String tomorrow = api.getRequest(LocalDate.now()
                                                  .plusDays(1), zone);

        //jackson to read our json
        ObjectMapper mapper = new ObjectMapper();
        List<ElectricityPrice> pricesToday = mapper.readValue(today, new TypeReference<>() {
        });
        List<ElectricityPrice> pricesTomorrow = mapper.readValue(tomorrow,
          new TypeReference<>() {
          });

        Scanner scanner = new Scanner(System.in);

        //creates the menu for our user
        MenuOptions.mainMenu(scanner, pricesToday, pricesTomorrow, zone);


        scanner.close();


    }


}
