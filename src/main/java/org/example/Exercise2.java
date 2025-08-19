package org.example;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

public class Exercise2 {
    static void main() throws ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Temperatur i celsius: ");
        String celsius = scanner.nextLine();

        try {
            double convert = Double.parseDouble(celsius) * 9 / 5 + 32;
            System.out.printf(Locale.US,"Temperatur i Fahrenheit: %.2f", convert);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + celsius);
        }
    }
}


/*
Uppgift 2: Temperaturkonvertering
Användaren matar in temperatur i Celsius Programmet konverterar
till Fahrenheit med formeln: F = C * 9 / 5 + 32 Skriv ut resultatet
*/
