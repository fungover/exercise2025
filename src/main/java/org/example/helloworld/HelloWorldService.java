package org.example.helloworld;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class HelloWorldService {

    List<String> guestBook = new CopyOnWriteArrayList<>();


    public Hello createHelloMessage(String name) {
        if ((name == null) || name.trim().isEmpty()) {
            name = "world";
        }
        else
            guestBook.add(name);
        return new Hello(name);
    }

    public List<String> guestBook() {
        return List.copyOf(guestBook);
    }
}
