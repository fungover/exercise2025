package org.example.game;

import java.util.Scanner;

public class GameLoop {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("> ");
            String command = scanner.nextLine();
        }
    }

}
