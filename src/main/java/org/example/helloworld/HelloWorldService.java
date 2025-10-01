package org.example.helloworld;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Qualifier;
import org.example.annotations.English;
import org.example.annotations.Log;
import org.example.annotations.Swedish;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
@Log
public class HelloWorldService {

    private Greeting greeting;
    Logger logger = Logger.getLogger(HelloWorldService.class);

    List<String> guestBook = new CopyOnWriteArrayList<>();

    @Inject
    public HelloWorldService(@Swedish Greeting greeting) {
        this.greeting = greeting;
    }

    public HelloWorldService() {
        logger.info("HelloWorldService instance created");
    }


    public Hello createHelloMessage(String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "world";
        } else
            guestBook.add(name);
        return new Hello(greeting.greet() + " " + name);
    }

    public List<String> guestBook() {
        return List.copyOf(guestBook);
    }
}
