package org.example;

/*
TODO:
    [x] *** Welcome message with info ***
    [] Fetch & display data from API
    [x] * Main menu: *
      [] * Select Zone *
          []  Today
                []    - All hours with prices
                []    - Mean price for current 24h period
                []    - Cheapest hour(s) & Most expensive hour(s) (earliest hour prioritized)
                []    - Show best time to consume electricity for:
                         - 2 hours
                         - 4 hours
                         - 8 hours
                []    - Exit to main menu
          []  Tomorrow
                []    - Error handling if not available
                []    - All available hours with prices
                []    - Mean price for tomorrow
                []    - Cheapest hour(s) & Most expensive hour(s) (earliest hour prioritized)
                []    - Show best time to consume electricity for:
                         - 2 hours
                         - 4 hours
                         - 8 hours
                []    - Exit to main menu
          []  Search (date)
                []    - All hours with prices
                []    - Mean price for selected date
                []    - Cheapest hour(s) & Most expensive hour(s)
                []    - Exit to main menu
          [] Exit to main menu
      [x] * Exit *
      [x] Error handling for menu
*/

import org.example.cli.Menu;

public class App {
    public static void main(String[] args) {
            Menu menu = new Menu();

            menu.start();
    }
}
