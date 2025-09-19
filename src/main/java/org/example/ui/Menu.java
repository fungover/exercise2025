package org.example.ui;

import org.example.util.TimeUtils;

public class Menu {
  // ----------func to print-------------
  public static void print(String message) {
    System.out.println(message);
  }

  // --------func for main menu UI----------
  // userName menu
  public static void userNameMenu() {
    print("|=====================[Welcome to El scoute]=====================|");
    print("|                                                                |");
    print("|        Here you will be able to scoute the following           |");
    print("|                                                                |");
    print("|   Checking the electricity price for different time and zone.  |");
    print("|         Scouting best possible time to charge your EV.         |");
    print("|       Scoute the cheapest and most expensive usage hours.      |");
    print("|                                                                |");
    print("|           Lets start by entering your name first               |");
    print("|                                                                |");
    print("|=======================[Enter your name]========================|");
    print("|");
    print("|");
    System.out.print("|~~~~~>:");
  }

  // Zone menu
  public static void zoneMenu(String userName) {
    // --dynamic spacing header--
    printDynamicHeader("Welcome " + userName);
    print("|                                                                |");
    print("|           Please select one of the area below                  |");
    print("|                                                                |");
    print("|     [1] if your scouting for [ Stockholm ] area                |");
    print("|     [2] if your scouting for [ Luleå ] area                    |");
    print("|     [3] if your scouting for [ Sundsvall ] area                |");
    print("|     [4] if your scouting for [ Malmö ] area                    |");
    print("|     [6] Exit program                                           |");
    print("|                                                                |");
    print("|======================[Select your area]========================|");
    print("|");
    print("|");
    System.out.print("|~~~~~>:");
  }

  // Main menu
  public static void mainMenu(String userName, String area) {
    printDynamicHeader("Menu for " + userName + " in " + area);
    print("|                                                                |");
    print("|          Please select one of the following option             |");
    print("|                                                                |");
    print("|     [1] Check todays average electricity cost for 24H period   |");
    print("|     [2] Scout the cheapest and most expensive hourly prices    |");
    print("|     [3] Scout the best time zone to charge your EV             |");
    print("|     [4] Check all hourly prices for today                      |");
    print("|     [5] Import consumption data from CSV file                  |");
    print("|     [6] Exit program                                           |");
    print("|                                                                |");
    print("|========================[Select option]=========================|");
    print("|");
    print("|");
    System.out.print("|~~~~~>:");
  }

  // Average price display menu
  public static void averagePriceMenu(String userName, String zoneName, String zoneId,
      int totalHours, double averagePrice) {
    spacer(20);
    printDynamicHeader("Mean price for " + userName + " in " + zoneName + " area");
    print("|                                                                |");
    print("|              AVERAGE PRICE ANALYSIS IS READY                   |");
    print("|                                                                |");
    String zoneInfo = "Zone  " + zoneName + " " + zoneId;
    String paddedZoneInfo = String.format("|        %-56s|", zoneInfo);
    print(paddedZoneInfo);
    print("|       The total hours analyzed is " + totalHours + "                           |");
    print(String.format("|       The average price in this time zone is: %.4f SEK/kWh   |", averagePrice));
    if (averagePrice < 0.20) {
      print("|       Great! Today has relatively low electricity prices.      |");
    } else if (averagePrice > 0.50) {
      print("|       Today has relatively high electricity prices!            |");
    } else {
      print("|       Today has moderate electricity prices.                   |");
    }
    print("|                                                                |");
    print("|                      [1] Back to main menu                     |");
    print("|                      [2] close the program                     |");
    print("|                                                                |");
    print("|========================[Select Option]=========================|");
    print("|");
    print("|");
    System.out.print("|~~~~~>:");
  }

  // Min/Max menu
  public static void minMaxPriceMenu(String userName, String zoneName,
      double cheapestPrice, String cheapestStart, String cheapestEnd,
      double expensivePrice, String expensiveStart, String expensiveEnd,
      double priceDifference) {
    spacer(20);
    printDynamicHeader("Price Analysis for " + userName + " in " + zoneName);
    print("|                                                                |");
    print("|              CHEAPEST AND MOST EXPENSIVE HOURS                 |");
    print("|                                                                |");
    print(String.format("|            ⬇️  CHEAPEST HOUR: %.4f SEK/kWh                    |", cheapestPrice));
    print(String.format("|                Time: %s to %s                            |", cheapestStart, cheapestEnd));
    print(String.format("|            ⬆️  MOST EXPENSIVE HOUR: %.4f SEK kWh              |", expensivePrice));
    print(String.format("|                Time: %s to %s                            |", expensiveStart, expensiveEnd));
    print(String.format("|            ⚖️  PRICE DIFFERENCE: %.4f SEK/kWh                 |", priceDifference));
    print("|                                                                |");
    if (priceDifference > 0.30) {
      print("|     💡 TIP: Consider timing energy-intensive activities!       |");
    } else {
      print("|    INFO: Price variation is relatively small today.          |");
    }
    print("|                   [1] Back to main menu                        |");
    print("|                   [2] Exit program                             |");
    print("|                                                                |");
    print("|========================[Select Option]=========================|");
    print("|");
    print("|");
    System.out.print("|~~~~~>:");
  }

  // EV Charging display menu
  public static void evChargingMenu(String userName, String zoneName,
      org.example.service.EVChargingOptimizer.ChargingWindow best2Hour,
      org.example.service.EVChargingOptimizer.ChargingWindow best4Hour,
      org.example.service.EVChargingOptimizer.ChargingWindow best8Hour) {
    spacer(20);
    printDynamicHeader("EV Charging Optimizer for " + userName + " in " + zoneName);
    print("|                                                                  |");
    print("|               BEST CHARGING TIMES FOR YOUR EV                    |");
    print("|                                                                  |");

    if (best2Hour != null) {
      print(String.format("|       🔋 2-HOUR CHARGING: %s to %s                         |",
          best2Hour.getStartTime(), best2Hour.getEndTime()));
      print(String.format("|          Average cost: %.4f SEK/kWh | Total: %.4f SEK        |",
          best2Hour.getAverageCost(), best2Hour.getTotalCost()));
    }

    if (best4Hour != null) {
      print("|                                                                  |");
      print(String.format("|       🔋 4-HOUR CHARGING: %s to %s                         |",
          best4Hour.getStartTime(), best4Hour.getEndTime()));
      print(String.format("|          Average cost: %.4f SEK/kWh | Total: %.4f SEK        |",
          best4Hour.getAverageCost(), best4Hour.getTotalCost()));
    }

    if (best8Hour != null) {
      print("|                                                                  |");
      print(String.format("|       🔋 8-HOUR CHARGING: %s to %s                         |",
          best8Hour.getStartTime(), best8Hour.getEndTime()));
      print(String.format("|          Average cost: %.4f SEK/kWh | Total: %.4f SEK        |",
          best8Hour.getAverageCost(), best8Hour.getTotalCost()));
    }

    print("|                                                                  |");
    print("|     💡TIP: Choose the duration that fits your charging needs!    |");
    print("|                       [1] Back to main menu                      |");
    print("|                       [2] Exit program                           |");
    print("|                                                                  |");
    print("|=========================[Select Option]==========================|");
    print("|");
    print("|");
    System.out.print("|~~~~~>:");
  }

  // All hourly prices menu
  public static void allHourlyPricesMenu(String userName, String zoneName,
      java.util.List<org.example.model.PriceData> prices) {
    spacer(20);
    printDynamicHeader("All Hourly Prices for " + userName + " in " + zoneName);
    print("|                                                                |");
    print("|               TODAY'S HOURLY ELECTRICITY PRICES                |");
    print("|                                                                |");

    for (int i = 0; i < Math.min(prices.size(), 24); i++) {
      org.example.model.PriceData price = prices.get(i);
      String timeRange = TimeUtils.toHHmm(price.getTime_start()) + "-" + TimeUtils.toHHmm(price.getTime_end());
      print(
          String.format("|                   %s: %.4f SEK/kWh                  |", timeRange, price.getSEK_per_kWh()));
    }

    print("|                                                                |");
    print("|              All prices shown in chronological order           |");
    print("|                       [1] Back to main menu                    |");
    print("|                       [2] Exit program                         |");
    print("|                                                                |");
    print("|========================[Select Option]=========================|");
    print("|");
    print("|");
    System.out.print("|~~~~~>:");
  }

  // CSV import display menu
  public static void csvImportMenu(String userName, String zoneName, int dataPoints, double totalConsumption,
      double totalCost) {
    spacer(20);
    printDynamicHeader("CSV Results for " + userName + " in " + zoneName);
    print("|                                                                |");
    print(String.format("|    Hours processed: %d                                      |", dataPoints));
    print(String.format("|    Total consumption: %.2f kWh                              |", totalConsumption));
    print(String.format("|    Total cost: %.2f SEK                                     |", totalCost));
    print("|                                                                |");
    print("|                   [1] Back to main menu                        |");
    print("|                   [2] Exit program                             |");
    print("|                                                                |");
    print("|========================[Select Option]=========================|");
    print("|");
    print("|");
    System.out.print("|~~~~~>:");
  } // -----------func to print spacer vertically-----------

  public static void spacer(int space) {
    for (int i = 0; i < space; i++) {
      System.out.println("                                :");
    }
    System.out.println();
  }

  // -----------func to create dynamic header-----------
  private static void printDynamicHeader(String headerText) {
    int headerLength = headerText.length();
    System.out.print("|");
    for (int i = 0; i < 30 - headerLength / 2; i++) {
      System.out.print("=");
    }
    System.out.print("[" + headerText + "]");
    for (int i = 0; i < 32 - headerLength / 2; i++) {
      System.out.print("=");
    }
    System.out.println("|");
  }
}
