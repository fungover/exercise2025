package org.example.service;

import org.example.model.PriceData;
import org.example.ui.Menu;
import org.example.util.TimeUtils;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;

public class EVChargingOptimizer {

  public static void showBestChargingTimes(String userName, String zoneName, String zoneId) {
    try {
      GetPrices service = new GetPrices(zoneId);
      List<PriceData> prices = service.findAllPrices();

      if (prices.isEmpty()) {
        System.out.println("No price data available.");
        return;
      }

      // --Find best charging windows for different durations--
      ChargingWindow best2Hour = findBestChargingWindow(prices, 2);
      ChargingWindow best4Hour = findBestChargingWindow(prices, 4);
      ChargingWindow best8Hour = findBestChargingWindow(prices, 8);

      // --Display results using Menu--
      Menu.evChargingMenu(userName, zoneName, best2Hour, best4Hour, best8Hour);

    } catch (Exception e) {
      System.out.println("Error finding optimal charging times: " + e.getMessage());
    }
  }

  // --Sliding window algorithm to find best charging period--
  private static ChargingWindow findBestChargingWindow(List<PriceData> prices, int durationHours) {
    // Input validation
    if (durationHours <= 0) {
      throw new IllegalArgumentException("Duration must be greater than 0, got: " + durationHours);
    }

    if (prices.size() < durationHours) {
      return null;
    }

    prices.sort(Comparator.comparing((PriceData price) -> {
      try {
        return OffsetDateTime.parse(price.getTime_start());
      } catch (Exception e) {
        return OffsetDateTime.MIN;
      }
    }));    double minTotalCost = Double.MAX_VALUE;
    int bestStartIndex = 0;

    // Sliding window algorithm
    for (int i = 0; i <= prices.size() - durationHours; i++) {
      double windowCost = 0.0;

      // Calculate total cost for current window
      for (int j = i; j < i + durationHours; j++) {
        windowCost += prices.get(j).getSEK_per_kWh();
      }

      // Update best window if current is cheaper
      if (windowCost < minTotalCost) {
        minTotalCost = windowCost;
        bestStartIndex = i;
      }
    }

    //--Create result object--
    PriceData startHour = prices.get(bestStartIndex);
    PriceData endHour = prices.get(bestStartIndex + durationHours - 1);
    double averageCost = minTotalCost / durationHours;

    return new ChargingWindow(
        TimeUtils.toHHmm(startHour.getTime_start()),
        TimeUtils.toHHmm(endHour.getTime_end()),
        averageCost,
        minTotalCost,
        durationHours);
  }

  // --Inner class to hold charging window results--
  public static class ChargingWindow {
    private final String startTime;
    private final String endTime;
    private final double averageCost;
    private final double totalCost;
    private final int duration;

    public ChargingWindow(String startTime, String endTime, double averageCost, double totalCost, int duration) {
      this.startTime = startTime;
      this.endTime = endTime;
      this.averageCost = averageCost;
      this.totalCost = totalCost;
      this.duration = duration;
    }

    public String getStartTime() {
      return startTime;
    }

    public String getEndTime() {
      return endTime;
    }

    public double getAverageCost() {
      return averageCost;
    }

    public double getTotalCost() {
      return totalCost;
    }

    public int getDuration() {
      return duration;
    }
  }
}
