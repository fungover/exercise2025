package org.example;

public class InMemoryMessageRepository  implements MessageRepository {
    @Override
    public String  getMessage() {
        return "Hello";
    }
}
