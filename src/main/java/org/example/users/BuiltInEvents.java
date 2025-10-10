package org.example.users;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class BuiltInEvents {
    public void onAppStart(@Observes @Initialized(ApplicationScoped.class) Object init) {
        System.out.println("Applikationen har startat!");
    }

    public void onRequestStart(@Observes @Initialized(RequestScoped.class) Object request){
        System.out.println("Incoming..........request" + request);
    }
}
