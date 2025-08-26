package org.example;

import java.io.IOException;

public class App {
    static void main(String[] args) {

        Fetch fetch = new Fetch();

        try {
            fetch.fetch();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
