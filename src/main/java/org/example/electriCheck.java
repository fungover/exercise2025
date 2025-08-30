package org.example;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.LocalDate;

public class electriCheck {

    static Scanner scanner = new Scanner(System.in);
    public static String zone = "";
    public static String date = formatedDate();


    public static void main(String[] args) {

        System.out.println("Welcome to ElectriCheck \n This is a simple electricity price checking software. \n This program will download the electricity prices for the upcoming 24 hours \n (note that upcoming prices are released after 1 PM so checking before that will not take the full 24 hours into account) \n");
        System.out.println("Please input your price zone? \n \n SE1 = Luleå / Norra Sverige \n SE2 = Sundsvall / Norra Mellansverige \n SE3 = Stockholm / Södra Mellansverige \n SE4 = Malmö / Södra Sverige \n");

        do {
            System.out.print("Zone: ");
            zone = scanner.nextLine();

            if (!zone.equalsIgnoreCase("SE1") && !zone.equalsIgnoreCase("SE2") && !zone.equalsIgnoreCase("SE3") && !zone.equalsIgnoreCase("SE4")) {
                System.out.println("Invalid input. Please try again");
            }
        } while (!zone.equalsIgnoreCase("SE1") && !zone.equalsIgnoreCase("SE2") && !zone.equalsIgnoreCase("SE3") && !zone.equalsIgnoreCase("SE4"));

        System.out.println(zone + date);
    }

    public static String formatedDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        return now.format(formatter);
    }
}
