package org.example.di.notify;

public class EmailNotifier implements Notifier {
    private final String from;

    public EmailNotifier(String from) {
        this.from = from;
    }

    @Override public void info(String message) {
        System.out.println("[EMAIL from " + from + "] " + message);
    }
}
