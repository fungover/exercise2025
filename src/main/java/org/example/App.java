package org.example;

/*
TODO:
    [x] *** Welcome message with info ***
    [] * Main menu *
      [] * Select Zone *
          []  Today (all hours)
                []    - Mean price for current 24h period
                []    - Cheapest hour(s) & Most expensive hour(s)
                []    - Show best time to consume electricity for:
                         - 2 hours
                         - 4 hours
                         - 8 hours
                []    - Exit to main menu
          []  Tomorrow
                []    - Error handling if not available
                []    - Mean price for tomorrow
                []    - Cheapest hour(s) & Most expensive hour(s)
                []    - Show best time to consume electricity for:
                         - 2 hours
                         - 4 hours
                         - 8 hours
                []    - Exit to main menu
          []  Search (date)
                []    - Mean price for selected date
                []    - Cheapest hour(s) & Most expensive hour(s)
                []    - Exit to main menu
          [] Exit to main menu
      [] * Exit *
      [] Error handling for menu
*/

public class App {
    public static void main(String[] args) {

        System.out.println("\n*************************************************");
        System.out.println("*** Welcome! We check prices for electricity! ***");
        System.out.println("*************************************************");
        System.out.println("\nSelect a zone to continue!");
        System.out.println("(Press [1]-[5] and then press Enter):");
        System.out.println("\n[1] SE1 = Luleå / Northern Sweden");
        System.out.println("[2] SE2 = Sundsvall / Northern Central Sweden");
        System.out.println("[3] SE3 = Stockholm / Southern Central Sweden");
        System.out.println("[4] SE4 = Malmö / Southern Sweden");
        System.out.println("[5] Exit");

    }
}
