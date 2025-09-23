package org.example;

import org.example.container.Container;

public class Main {
    public static void main(String[] args) {

        App app = Container.getInstance(App.class);

        app.processMessage("Part 2 test :)");
    }
}
