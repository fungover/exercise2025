package org.fungover.model;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.fungover.core.Greeting;

@Dependent
public class Greeter {
    private final Greeting greeting;

    @Inject
    public Greeter(Greeting greeting) {
        this.greeting = greeting;
    }

    public void run() {
        System.out.println(greeting.greet("oscar"));
    }
}
