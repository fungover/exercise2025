package org.fungover.model;

import org.fungover.core.Greeting;

public class Greeter {
    private final Greeting greeting;

    public Greeter(Greeting greeting) {
        this.greeting = greeting;
    }

    public void run() {
        System.out.println(greeting.greet("oscar"));
    }
}
