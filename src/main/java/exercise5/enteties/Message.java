package exercise5.enteties;

import jakarta.inject.Inject;

public class Message {
    String message;
    @Inject
    public Message(String message) {
        this.message = message;
    }
}
