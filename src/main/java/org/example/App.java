package org.example;
import org.example.menus.AreaMenu;
import java.util.Scanner;

//TODO List
//Create boilerplate for project
//Setup menu for Area selection
//Todo: Setup main manu selection (A exit option should be included)
//Todo: Setup URL Builder
//Todo: Setup API request for electricity prices data
//Todo: Setup error handling for failed http request etc
//Todo: Implement mean price calculation for current 24h
//Todo: Implement logic to find cheapest and most expensive hour (earliest if tie)
//Todo: Implement sliding window algorithm for best charging time (2, 4, 8 hours)
//Todo: Format and print results clearly to user
//Todo: (Optional) Add CSV import for consumption data and calculate total cost

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to my Command-Line Interface that help you optimize your electricity consumption and reduce costs!\n");

        Scanner scanner = new Scanner(System.in);
        AreaMenu areaMenu = new AreaMenu(scanner);

        String area = areaMenu.getArea();

        System.out.println("Area is " + area);
    }
}
