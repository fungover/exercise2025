package org.SpinalGlitter.exercise2;

import org.SpinalGlitter.exercise2.game.Game;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String asciiArt = """
                ________                                             _________                      .__                 _________ .____    .___\s
                \\______ \\  __ __  ____    ____   ____  ____   ____   \\_   ___ \\____________ __  _  _|  |   ___________  \\_   ___ \\|    |   |   |
                 |    |  \\|  |  \\/    \\  / ___\\_/ __ \\/  _ \\ /    \\  /    \\  \\/\\_  __ \\__  \\\\ \\/ \\/ /  | _/ __ \\_  __ \\ /    \\  \\/|    |   |   |
                 |    `   \\  |  /   |  \\/ /_/  >  ___(  <_> )   |  \\ \\     \\____|  | \\// __ \\\\     /|  |_\\  ___/|  | \\/ \\     \\___|    |___|   |
                /_______  /____/|___|  /\\___  / \\___  >____/|___|  /  \\______  /|__|  (____  /\\/\\_/ |____/\\___  >__|     \\______  /_______ \\___|
                        \\/           \\//_____/      \\/           \\/          \\/            \\/                 \\/                \\/        \\/   \s
                """;
        try (Scanner inputScanner = new Scanner(System.in)) {
            System.out.println(asciiArt);
            System.out.println("What do you want to name your character?");
            System.out.print("> ");
            String name = inputScanner.nextLine();
            System.out.println("What difficulty do you want to play (easy/medium/hard)? (default = easy)");
            System.out.print("> ");
            String difficulty = inputScanner.nextLine();
            Game game = new Game(name, difficulty);
            System.out.println();
            game.startGame();
        }
    }
}
