package org.example;

import java.util.Random;
import java.util.Scanner;

public class Exercise5 {

    static void main() {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int magicNumber = random.nextInt(1,101);
        int guessCount = 0;
        boolean notCompleted = true;

        do {
            System.out.println("Guess the magic number!");
            int guess = scanner.nextInt();
            guessCount++;

            if (guess == magicNumber) {
                System.out.println("You guessed it right!");
                notCompleted = false;
            } else if (guess < magicNumber) {
                System.out.println("Too low!");
            } else {
                System.out.println("Too high");
            }

        } while (notCompleted);

        System.out.println(guessCount + " tries");
        /*'är detta logisk? ta gärna över någon*/
    }
}





/*
Uppgift 5: Gissa talet
Programmet har ett hemligt tal (t.ex. 42 eller ett slumpmässigt tal)
Användaren gissar tills rätt tal anges Programmet ger feedback: "För högt", "För lågt", "Rätt!"
Räkna antal försök och skriv ut när användaren gissat rätt*/
