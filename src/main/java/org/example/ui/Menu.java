package org.example.ui;

public class Menu {
  /**
   * Prints the given message followed by a newline to standard output.
   *
   * @param message the text to print
   */
  public static void print(String message) {
    System.out.println(message);
  }

  // --------func for main menu UI----------
  /**
   * Prints the welcome screen prompting the user to enter their name.
   *
   * <p>Renders a framed introductory menu describing the app's capabilities and emits an input
   * prompt (using System.out.print) ready for the user's name. This method performs console
   * output only; it does not read or validate input.
   */
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

  /**
   * Renders the zone selection screen to standard output for the given user.
   *
   * Displays a decorative header that includes the user's name, a list of predefined
   * area choices, and an inline prompt left open for user input.
   *
   * @param userName the display name to show in the menu header
   */
  public static void zoneMenu(String userName) {
    //--dynamic spacing header--
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

  /**
   * Render the main interactive menu for a given user and selected area to standard output.
   *
   * The menu includes a dynamic header containing the user's name and area and lists available
   * numbered actions (average price, min/max price, EV charging windows, hourly prices, CSV import,
   * and exit). Prompts the user to select an option via a terminal prompt.
   *
   * @param userName the display name of the current user shown in the header
   * @param area     the selected area/zone shown in the header
   */
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

  /**
   * Render the average-price analysis screen to standard output for a user and zone.
   *
   * Prints a framed, fixed-format menu that summarizes the number of analyzed hours
   * and the mean electricity price for the specified zone, and then prompts the
   * user to select an option. The displayed average price is formatted to four
   * decimal places (SEK/kWh). A short contextual message is shown depending on
   * the price level: low (< 0.20), moderate (0.20–0.50), or high (> 0.50).
   *
   * Side effects:
   * - Writes formatted lines and a selection prompt to System.out.
   *
   * @param userName     the user's display name shown in the header
   * @param zoneName     the human-readable name of the price zone
   * @param zoneId       the zone identifier (e.g., zone code) shown alongside the name
   * @param totalHours   the number of hourly data points included in the average
   * @param averagePrice the computed average price in SEK per kWh
   */
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

  /**
   * Renders a console menu showing the cheapest and most expensive electricity hours for a zone.
   *
   * <p>Displays formatted cheapest and most expensive hour prices with their start/end times,
   * the numeric price difference, and a contextual tip when the difference exceeds 0.30 SEK/kWh.
   *
   * @param userName the name of the user shown in the header
   * @param zoneName the zone name shown in the header
   * @param cheapestPrice the price (SEK/kWh) of the cheapest hour
   * @param cheapestStart human-readable start time for the cheapest hour (e.g., "08:00")
   * @param cheapestEnd human-readable end time for the cheapest hour (e.g., "09:00")
   * @param expensivePrice the price (SEK/kWh) of the most expensive hour
   * @param expensiveStart human-readable start time for the most expensive hour
   * @param expensiveEnd human-readable end time for the most expensive hour
   * @param priceDifference the numeric difference between expensivePrice and cheapestPrice (SEK/kWh)
   */
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

  /**
   * Render the EV charging optimizer menu for a user in a given zone.
   *
   * <p>Displays up to three recommended charging windows (2-hour, 4-hour, 8-hour) with their
   * start/end times, average cost (SEK/kWh) and total cost. Any charging window argument that is
   * null is omitted from the output. Ends by prompting the user to select an option.
   */
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

  /**
   * Renders and prints a framed "All Hourly Prices" menu for the given user and zone to standard output.
   *
   * <p>The menu shows up to the first 24 entries from {@code prices} in chronological order,
   * formatting each entry as "HH:MM-HH:MM: x.xxxx SEK/kWh". Numeric prices are formatted to
   * four decimal places. The method performs console output only and does not return a value.
   *
   * @param userName  display name used in the menu header
   * @param zoneName  display zone used in the menu header
   * @param prices    chronological list of PriceData entries; at most the first 24 items are displayed
   */
  public static void allHourlyPricesMenu(String userName, String zoneName, java.util.List<org.example.model.PriceData> prices) {
    spacer(20);
    printDynamicHeader("All Hourly Prices for " + userName + " in " + zoneName);
    print("|                                                                |");
    print("|               TODAY'S HOURLY ELECTRICITY PRICES                |");
    print("|                                                                |");

    for (int i = 0; i < Math.min(prices.size(), 24); i++) {
      org.example.model.PriceData price = prices.get(i);
      String timeRange = price.getTime_start().substring(11, 16) + "-" + price.getTime_end().substring(11, 16);
      print(String.format("|                   %s: %.4f SEK/kWh                  |", timeRange, price.getSEK_per_kWh()));
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

  /**
   * Render a CSV import results screen to the console.
   *
   * Displays a framed summary including the user and zone, number of processed data points,
   * total energy consumption (kWh) and total cost (SEK), followed by menu options to return
   * to the main menu or exit.
   *
   * @param userName         the user's display name shown in the header
   * @param zoneName         the zone/area name shown in the header
   * @param dataPoints       number of CSV rows/hours processed
   * @param totalConsumption total energy consumed from the imported data, in kWh
   * @param totalCost        total cost computed from the imported data, in SEK
   */
  public static void csvImportMenu(String userName, String zoneName, int dataPoints, double totalConsumption, double totalCost) {
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
  }  /**
   * Inserts vertical spacing into the console output.
   *
   * Prints `space` lines containing a fixed indentation and a colon, then prints one additional blank line.
   *
   * @param space the number of spacer lines to print; if `space` is zero or negative, no indented lines are printed and only the trailing blank line is emitted
   */
  public static void spacer(int space) {
    for (int i = 0; i < space; i++) {
      System.out.println("                                :");
    }
    System.out.println();
  }

  /**
   * Prints a decorative, framed header line containing the provided text to standard output.
   *
   * The header is centered approximately by surrounding the text with '=' padding inside vertical
   * bars (e.g. |======[Header]=======|). Designed as a private helper for consistent menu headers.
   *
   * @param headerText the text to display inside the framed header
   */
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
