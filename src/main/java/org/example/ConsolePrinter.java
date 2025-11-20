package org.example;

public class ConsolePrinter implements Printer {
    @Override
    public void print(String message) {
        System.out.println("Printer output: " + message);
    }
}
