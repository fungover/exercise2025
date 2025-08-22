package org.example.util;

import java.util.List;

public class PriceUtils {

    public static void priceCheck(List<ElectricityPrice> prices) {
        for (ElectricityPrice price : prices) {
            System.out.printf("%s -> %.3f SEK/kWh%n", price.time_start.substring(11, 16),
              price.SEK_per_kWh);
        }
    }

    public static double avgPrice(List<ElectricityPrice> prices) {
        //TODO done 3.Print the mean price for the current 24-hour period.
        double sum = 0.0;
        for (ElectricityPrice price : prices) {
            sum += price.SEK_per_kWh;
        }
        return prices.isEmpty() ? 0.0 : sum / prices.size();
    }

    public static void checkMinMaxPrice(List<ElectricityPrice> prices) {
        //check if empty or null
        if (prices == null || prices.isEmpty()) {
            System.out.println("Unable to find a price list for this day");
            return;
        }

        //set min and max variables to the first thing in the list to start with
        ElectricityPrice min = prices.get(0);
        ElectricityPrice max = prices.get(0);

        //loop through each price
        for (ElectricityPrice price : prices) {
            //if a price is lower the the current min, update
            if (price.SEK_per_kWh < min.SEK_per_kWh) {
                min = price;
            } //if the price is the same check what time is earlier
            else if (price.SEK_per_kWh == min.SEK_per_kWh &&
              price.time_start.compareTo(min.time_start) < 0) {
                min = price;
            }

            //same here but for higher
            if (price.SEK_per_kWh > max.SEK_per_kWh) {
                max = price;
            } else if (price.SEK_per_kWh == max.SEK_per_kWh &&
              /*compareTo returns a 0 if same and a negative number if the first is earlier
                and a positive if its later
               */
              price.time_start.compareTo(max.time_start) < 0) {
                max = price;
            }
        }

        System.out.printf("Lowest price: %.3f SEK/kWh at %s%n", min.SEK_per_kWh,
          min.time_start.substring(11, 16));
        System.out.printf("Highest price: %.3f SEK/kWh at %s%n", max.SEK_per_kWh,
          max.time_start.substring(11, 16));
    }

    public static void findBestChargingTime(List<ElectricityPrice> prices, int durationHours) {

    }
}
