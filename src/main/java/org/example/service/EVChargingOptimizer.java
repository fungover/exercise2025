package org.example.service;

import org.example.model.PriceData;
import org.example.ui.Menu;
import java.util.List;

public class EVChargingOptimizer {

  /**
   * Fetches electricity price data for a zone, computes optimal EV charging windows for 2, 4 and 8 hours,
   * and delegates display of the results to the UI menu.
   *
   * If no price data is available the method prints a notice and returns. Any unexpected errors are caught
   * and reported to stdout.
   *
   * @param userName  display name of the user (passed through to the menu)
   * @param zoneName  human-readable zone name (passed through to the menu)
   * @param zoneId    identifier used to fetch price data for the zone
   */
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
      System.out.println("❌ Error finding optimal charging times: " + e.getMessage());
    }
  }

  /**
   * Finds the contiguous charging window of the given duration with the lowest total cost.
   *
   * <p>Scans the list of hourly price entries using a sliding window of length {@code durationHours}
   * and returns a {@link ChargingWindow} representing the cheapest contiguous block. If the list
   * contains fewer entries than {@code durationHours}, this method returns {@code null}.
   *
   * @param prices        list of hourly {@code PriceData} entries (expected in chronological order)
   * @param durationHours number of consecutive hours for the charging window
   * @return a {@link ChargingWindow} for the cheapest window, or {@code null} if there is not enough data
   */
  private static ChargingWindow findBestChargingWindow(List<PriceData> prices, int durationHours) {
    if (prices.size() < durationHours) {
      return null;
    }

    double minTotalCost = Double.MAX_VALUE;
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
        startHour.getTime_start().substring(11, 16),
        endHour.getTime_end().substring(11, 16),
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

    /**
     * Creates an immutable ChargingWindow describing a contiguous charging period.
     *
     * @param startTime   formatted start time for the window (expected "HH:mm")
     * @param endTime     formatted end time for the window (expected "HH:mm")
     * @param averageCost average price in SEK per kWh over the window
     * @param totalCost   total price in SEK for charging across the full window
     * @param duration    window length in hours
     */
    public ChargingWindow(String startTime, String endTime, double averageCost, double totalCost, int duration) {
      this.startTime = startTime;
      this.endTime = endTime;
      this.averageCost = averageCost;
      this.totalCost = totalCost;
      this.duration = duration;
    }

    /**
     * Returns the start time of the charging window in "HH:mm" format.
     *
     * @return the window start time as a string (e.g., "08:00")
     */
    public String getStartTime() {
      return startTime;
    }

    /**
     * Returns the end time of the charging window.
     *
     * The value is a time string in "HH:mm" format representing the window's end (derived from the source price timestamps).
     *
     * @return end time as an "HH:mm" string
     */
    public String getEndTime() {
      return endTime;
    }

    /**
     * Returns the average cost per kWh for this charging window.
     *
     * @return the average cost (SEK per kWh) across the window
     */
    public double getAverageCost() {
      return averageCost;
    }

    /**
     * Returns the total cost for this charging window.
     *
     * @return the total cost (SEK) to charge for the full window
     */
    public double getTotalCost() {
      return totalCost;
    }

    /**
     * Returns the duration of this charging window in hours.
     *
     * @return duration in hours
     */
    public int getDuration() {
      return duration;
    }
  }
}
