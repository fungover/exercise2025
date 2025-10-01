package org.example.helloworld;

import jakarta.enterprise.context.RequestScoped;
import org.example.annotations.English;
import org.example.annotations.Swedish;

public interface Greeting {
    String greet();
}

@RequestScoped
@Swedish
class SwedishGreeting implements Greeting {
    @Override
    public String greet() {
        return "Hej";
    }
}

@RequestScoped
@English
class EnglishGreeting implements  Greeting {
    @Override
    public String greet() {
        return "Hello";
    }
}