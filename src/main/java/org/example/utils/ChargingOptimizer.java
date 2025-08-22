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

          //step 1: Convert json to java object.

          ObjectMapper mapper = JsonMapper.get(); // Create an instance of ObjectMapper to parse the JSON string.
          List<ElectricityPrice> prices = mapper.readValue(json, new TypeReference<>() {});

          // Step 2: validate input
          if (prices.size() < chargingHours) {
              return null;
          }

          // step 3: Calculate the first windows sum, (for example first 2 hours).

          BigDecimal currentWindowSum = BigDecimal.ZERO; // Starting with 0.
          for (int i = 0; i < chargingHours; i++) {
              currentWindowSum = currentWindowSum.add(prices.get(i).sekPerKwh()); // Add each price in the first window to the currentWindowSum.
          }

          // step 4: set the first window as the "best one yet".
          BigDecimal bestSum = currentWindowSum;
          int bestStartIndex = 0; // the index where the best window starts.

          //Step 5: SLIDING WINDOW - slide window over the rest of data.
          // we start from position 1 and go to the end of the list.

          for (int i = 1; i <= prices.size() - chargingHours; i++) {

              // Remove the element that "falls out" from the left side of the window.
              currentWindowSum = currentWindowSum.subtract(prices.get(i - 1).sekPerKwh());

              // Add the new element that "enter" from the right side of the window.
              currentWindowSum = currentWindowSum.add(prices.get(i + chargingHours - 1).sekPerKwh());

              // compare the best one yet - is this sum less (cheaper).
              if (currentWindowSum.compareTo(bestSum) < 0) {
                  bestSum = currentWindowSum; // update best sum
                  bestStartIndex = i; // update the index where the best window starts
              }
          }

          // step 6: Build the result-objekt with the best period we found.
          OffsetDateTime bestStartTime = prices.get(bestStartIndex).timeStart();
          OffsetDateTime bestEndTime = prices.get(bestStartIndex + chargingHours - 1).timeEnd();

          return new ChargingResult(bestStartTime, bestEndTime, bestSum, chargingHours);

      } catch (Exception e) {
          System.err.println("Error while parsing json: " + e.getMessage());
          return null;
      }
    }


}
