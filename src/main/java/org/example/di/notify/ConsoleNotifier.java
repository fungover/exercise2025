package org.example.di.notify;

public class ConsoleNotifier implements Notifier {
    @Override public void info(String message) {
        System.out.println("[INFO] " + message);
    }
}
