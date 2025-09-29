package org.example.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PriceUtils {

    public static void priceCheck(List<ElectricityPrice> prices) {
        if (prices == null || prices.isEmpty()) {
            System.out.println("No prices to display");
            System.out.println("-".repeat(20));
            return;
        }
        for (ElectricityPrice price : prices) {
            System.out.printf("%s -> %.3f SEK/kWh%n", price.time_start.substring(11, 16),
              price.SEK_per_kWh);
        }
        System.out.println("-".repeat(20));
    }

    public static BigDecimal avgPrice(List<ElectricityPrice> prices) {
        //TODO done 3.Print the mean price for the current 24-hour period.
        if (prices == null || prices.isEmpty()) {
            return BigDecimal.ZERO.setScale(5, RoundingMode.HALF_UP);
        }
        BigDecimal sum = BigDecimal.ZERO;
        for (ElectricityPrice price : prices) {
            sum = sum.add(price.SEK_per_kWh);
        }
        return sum.divide(BigDecimal.valueOf(prices.size()), 5, RoundingMode.HALF_UP);
    }

    public static void checkMinMaxPrice(List<ElectricityPrice> prices) {
        //check if empty or null
        if (prices == null || prices.isEmpty()) {
            System.out.println("Unable to find a price list for this day");
            return;
        }

        //set min and max variables to the first thing in the list to start with
        ElectricityPrice min = prices.getFirst();
        ElectricityPrice max = prices.getFirst();

        //loop through each price
        for (ElectricityPrice price : prices) {
            //if a price is lower the current min, update
            if (price.SEK_per_kWh.compareTo(min.SEK_per_kWh) < 0) {
                min = price;
            } //if the price is the same check what time is earlier
            else if (price.SEK_per_kWh.compareTo(min.SEK_per_kWh) == 0 &&
              price.time_start.compareTo(min.time_start) < 0) {
                min = price;
            }

            //same here but for higher
            if (price.SEK_per_kWh.compareTo(max.SEK_per_kWh) > 0) {
                max = price;
            } else if (price.SEK_per_kWh.compareTo(max.SEK_per_kWh) == 0 &&
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
        System.out.println("-".repeat(20));
    }

    public static void findBestChargingTime(List<ElectricityPrice> prices,
                                            int[] durationList) {
        /*TODO 6.Determine the best time to charge an electric car for durations of 2, 4, or 8 hours.
           durationList will be [2 ,4 ,8]*/

        //we run this once for every set of hours (3)
        for (int durationHour : durationList) {
            //check if duration is bigger then the list
            if (prices == null || prices.size() < durationHour) {
                System.out.printf("Not enough data to compute a %dh charging window.%n",
                  durationHour);
                continue;
            }


            BigDecimal minSum;
            int bestTimeToStart;

            //Calculate sum for first window
            //this is our baseline that we will compare all other windows to
            BigDecimal currentSum = BigDecimal.ZERO;
            for (int i = 0; i < durationHour; i++) {
                currentSum = currentSum.add(prices.get(i).SEK_per_kWh);
            }

            minSum = currentSum;
            bestTimeToStart = 0;
            //Slide the window
            for (int i = 1; i <= prices.size() - durationHour; i++) {
                /* remove the sum that's leaving and add the new one*/
                currentSum = currentSum.subtract(prices.get(i - 1).SEK_per_kWh)
                                       .add(prices.get(i + durationHour - 1).SEK_per_kWh);
                //check if the new window is better than out current best
                if (currentSum.compareTo(minSum) < 0) {
                    minSum = currentSum;
                    bestTimeToStart = i;
                }
            }
            if (bestTimeToStart != -1) {
                ElectricityPrice startHour = prices.get(bestTimeToStart);
                int endIdxInclusive = bestTimeToStart + durationHour - 1;
//                ElectricityPrice endHour = prices.get(bestTimeToStart + durationHour);

                //display-ready end time
                String endAt;
                if (endIdxInclusive + 1 < prices.size()) {
                    endAt = prices.get(endIdxInclusive + 1).time_start.substring(11, 16);
                } else {
                    endAt = "24:00";
                }


                System.out.printf(
                  "charging for %dh: Start at %s, end at %s (total cost: %.3f SEK)%n",
                  durationHour, startHour.time_start.substring(11, 16), endAt, minSum);
            }
            //pain in the butt!

        }

    }
}
