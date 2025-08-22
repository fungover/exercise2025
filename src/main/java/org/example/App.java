package org.example;
import org.example.menus.AreaMenu;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("Welcome to my Command-Line Interface that help you optimize your electricity consumption and reduce costs!\n");

        Scanner scanner = new Scanner(System.in);
        AreaMenu areaMenu = new AreaMenu(scanner);

        String area = areaMenu.getArea();

        System.out.println("Area is " + area);
    }
}
