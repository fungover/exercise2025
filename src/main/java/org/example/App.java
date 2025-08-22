package org.example;
import org.example.menus.AreaMenu;
import org.example.menus.MainMenu;
import java.util.Scanner;

//TODO List
//Create boilerplate for project
//Setup menu for Area selection
//Todo: Setup main menu selection (A exit option should be included)
//Todo: Setup URL Builder
//Todo: Setup API request for electricity prices data
//Todo: Setup error handling for failed http request etc
//Todo: Implement mean price calculation for current 24h
//Todo: Implement logic to find cheapest and most expensive hour (earliest if tie)
//Todo: Implement sliding window algorithm for best charging time (2, 4, 8 hours)
//Todo: Format and print results clearly to user
//Todo: (Optional) Add CSV import for consumption data and calculate total cost

public class App {
    private static final String[] AREAS = {
            "Luleå / Northern Sweden",
            "Sundsvall / Northern Central Sweden",
            "Stockholm / Southern Central Sweden",
            "Malmö / Southern Sweden"
    };

    private static final String[] OPTIONS = {
            "Download prices for today/next day",
            "Print mean price for current 24h",
            "Show cheapest & most expensive hour",
            "Best charging window (2/4/8h)",
            "Exit"
    };

    public static void main(String[] args) {
        System.out.println("Welcome to my Command-Line Interface that helps you optimize your electricity consumption and reduce costs!\n");
        Scanner scanner = new Scanner(System.in);

        AreaMenu areaMenu = new AreaMenu(scanner, AREAS);
        String area = areaMenu.getArea();

        MainMenu mainMenu = new MainMenu(scanner, OPTIONS);
        int option = mainMenu.getOption();

        System.out.println("Area is " + area);
        System.out.println("Option is " + option);
    }
}
