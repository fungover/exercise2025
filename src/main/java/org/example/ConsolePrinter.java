package org.example;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConsolePrinter implements Printer {

    @Override
    public void print(String message) {
        System.out.println("Printer output: " + message);
    }
}
