package org.example.di.notify;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.di.qualifiers.Console;

@ApplicationScoped
@Console
public class ConsoleNotifier implements Notifier {
    @Override public void info(String message) {
        System.out.println("[INFO] " + message);
    }
}
