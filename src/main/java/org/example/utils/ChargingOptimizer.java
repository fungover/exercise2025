package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.ChargingResult;
import org.example.model.ElectricityPrice;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class ChargingOptimizer {

    /**
     * This class is going to implement the Sliding window Algoritm with 0(n) complexity.
     * It will be used to find the best time to charge the car based on the electricity prices
     * for 2, 4 and 8 hours window.
     */

    public static ChargingResult findBestChargingTime(String json, int chargingHours) {

      try {

          //Convert json to java object.

          ObjectMapper mapper = JsonMapper.get(); // Create an instance of ObjectMapper to parse the JSON string.
          List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {});

          // Step 2: validate input
          if (prices.size() < chargingHours) {
              return null;
          }

          int startIndex = PriceDataUtils.findCurrentOrNextHourIndex(prices);

          int availableHours = prices.size() - startIndex;
          if (availableHours < chargingHours) {
              return null;
          }

          // Calculate the first windows sum, (for example first 2 hours).

          BigDecimal currentWindowSum = BigDecimal.ZERO; // Starting with 0.
          for (int i = startIndex; i < startIndex + chargingHours; i++) { //we loop through the number of hours the user choose to charge.
              currentWindowSum = currentWindowSum.add(prices.get(i).sekPerKwh()); // Add each price in the first window to the currentWindowSum.
          }

          // set the first window as the "best one yet".
          BigDecimal bestSum = currentWindowSum;
          int bestStartIndex = startIndex; // the index where the best window starts.



          /**
           * Here we start at index 1, since index 0 is the first window we already calculated.
           * Then we continue as long as we can fit a full window of chargingHours.
           * So when using prices.size() - chargingHours, we get for example 24 - 4 = 20. meaning the last window starts at index 20 and ends at index 23.
           */

          for (int i = startIndex + 1; i <= prices.size() - chargingHours; i++) {

              // Remove the element that "falls out" from the left side of the window.
              // example: [1,2,3] -> [2,3,4] we remove 1 and add 4.
              currentWindowSum = currentWindowSum.subtract(prices.get(i - 1).sekPerKwh());

              // Add the new element that "enter" from the right side of the window.
               // Here we add the last element for example number 4 in [2,3,4].
              currentWindowSum = currentWindowSum.add(prices.get(i + chargingHours - 1).sekPerKwh());

              // compare the best one yet - is this sum less (cheaper).
              if (currentWindowSum.compareTo(bestSum) < 0) {
                  bestSum = currentWindowSum; // update best sum
                  bestStartIndex = i; // update the index where the best window starts
              }
          }

          // Build the result-objekt with the best period we found.
          OffsetDateTime bestStartTime = prices.get(bestStartIndex).timeStart();
          OffsetDateTime bestEndTime = prices.get(bestStartIndex + chargingHours - 1).timeEnd();

          return new ChargingResult(bestStartTime, bestEndTime, bestSum, chargingHours);

      } catch (Exception e) {
          System.err.println("Error while parsing json: " + e.getMessage());
          return null;
      }
    }
}
