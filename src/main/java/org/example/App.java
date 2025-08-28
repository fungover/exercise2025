package org.example;

/*
TODO:
    [x] *** Welcome message with info ***
    [x] Fetch & display data from API
    [x] * Main menu: *
      [x] * Select Zone *
          []  Today
                [x]    - All hours with prices
                [x]    - Mean price for current 24h period
                [x]    - Cheapest hour(s) & Most expensive hour(s) (earliest hour prioritized)
                []    - Show best time to consume electricity for:
                         - 2 hours
                         - 4 hours
                         - 8 hours
                [x]    - Exit to main menu
          []  Tomorrow
                [x]    - Error handling if not available
                []    - All available hours with prices
                []    - Mean price for tomorrow
                []    - Cheapest hour(s) & Most expensive hour(s) (earliest hour prioritized)
                []    - Show best time to consume electricity for:
                         - 2 hours
                         - 4 hours
                         - 8 hours
                [x]    - Exit to main menu
          [x]  Search (date)
                [x]    - All hours with prices
                [x]    - Mean price for selected date
                [x]    - Cheapest hour(s) & Most expensive hour(s)
                [x]    - Exit to main menu
          [x] Exit to main menu
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
