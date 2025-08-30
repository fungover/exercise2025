package org.example;

import org.example.data.PriceDataManager;
import org.example.menu.InputHandler;
import org.example.menu.Menu;
import org.example.menu.MenuAction;
import org.example.menu.actions.*;

import java.util.List;

public class App {
    public static void main(String[] args) {
        String initialZone = args.length > 0 ? args[0] : "SE4";
        PriceDataManager dataManager = new PriceDataManager();
        dataManager.setZone(initialZone);
        InputHandler inputHandler = new InputHandler();

        List<MenuAction> actions = List.of(
                new DownloadPricesAction(dataManager, inputHandler),
                new MeanPriceAction(dataManager),
                new ExtremeHoursAction(dataManager),
                new BestChargingTimeAction(dataManager, inputHandler),
                new SelectZoneAction(dataManager, inputHandler)
        );

        Menu menu = new Menu(actions);
        while (true) {
            menu.display();
            int choice = inputHandler.getUserChoice(actions.size());
            MenuAction action = menu.getAction(choice);
            if (action != null) {
                action.execute();
            } else {
                System.out.println("Invalid choice, please try again.");
            }
        }
    }
}

//##ASSIGNMENT:

//Electricity prices vary hour to hour, and sometimes the difference can be quite substantial.
// To help us optimize when to turn on the sauna or charge electric cars, we want a small CLI program that provides the necessary information.
//
//We can retrieve electricity prices—both historical and for the coming 24 hours—from the Elpris API.
//
//✅ Requirements
//Create a CLI (Command-Line Interface) program that can:
//
//Download prices for the current day and the next day (if available). X
//
//Print the mean price for the current 24-hour period. X
//
//Identify and print the hours with the cheapest and most expensive prices.
//
//If multiple hours share the same price, select the earliest hour.
//
//Determine the best time to charge an electric car for durations of 2, 4, or 8 hours. (Use a Sliding Window algorithm for this.)
//
//Allow selection of the price zone ("zon") for which to retrieve data. (Possible input methods: command-line argument, config file, or interactive prompt.)
//
//⭐ Extra Credit
//Enable importing actual hourly consumption data from a CSV file and calculate the total cost.