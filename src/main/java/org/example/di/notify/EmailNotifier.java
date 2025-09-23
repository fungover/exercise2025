package org.example.di.notify;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.di.qualifiers.Email;

@ApplicationScoped
@Email
public class EmailNotifier implements Notifier {
    private final String from;

    @Inject
    public EmailNotifier(@Email String from) {
        this.from = from;
    }

    @Override public void info(String message) {
        System.out.println("[EMAIL from " + from + "] " + message);
    }
}
