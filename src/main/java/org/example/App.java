package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.ElprisApi;
import org.example.cli.ZonePicker;
import org.example.util.ElectricityPrice;
import org.example.util.PriceUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class App {
    ZonePicker zonePicker = new ZonePicker();

    public static void main(String[] args) throws Exception {
        ElprisApi api = new ElprisApi();
        String zone = new ZonePicker().getZone();
        String today = api.getRequest(LocalDate.now(), zone);
        String tomorrow = api.getRequest(LocalDate.now()
                                                  .plusDays(1), zone);

        ObjectMapper mapper = new ObjectMapper();
        List<ElectricityPrice> pricesToday = mapper.readValue(today, new TypeReference<>() {
        });
        List<ElectricityPrice> pricesTomorrow = mapper.readValue(tomorrow,
          new TypeReference<>() {
          });

        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("\nSelected Zone " + zone);
            System.out.println("""
                               1. Average price for the day in your zone
                               2. Full list of the price for the day in your zone
                               5. Exit
                               """);
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();

                switch (input) {
                    case 1 -> System.out.printf(
                      "the average price for the day\nin your zone is %" + ".3f SEK/kWh\n",
                      PriceUtils.avgPrice(pricesToday));
                    case 2 -> PriceUtils.priceCheck(pricesToday);
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice, please try " + "again");
                }
            } else {
                System.out.println(
                  "Invalid choice, please input a number between 1 and 4 or 5" + " to exit.");
                scanner.next();
            }
        }
        scanner.close();


    }


}
