package org.example;

import org.example.game.GameEngine;

public class App {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        if (args.length == 0 || "random".equalsIgnoreCase(args[0])) {
            engine.start();  // <- uses the no-arg convenience
        } else {
            long seed = Long.parseLong(args[0]);
            engine.start(seed); // <- reproducible run
        }
    }
}
