package impl;

import api.Greetings;
import api.MessageRepository;
import jakarta.inject.Inject;

public class GreetingsImpl implements Greetings {
    private final MessageRepository repo;

    @Inject
    public GreetingsImpl(MessageRepository repo) {
        this.repo = repo;
    }

    @Override
    public void greet(String name) {
        System.out.println(repo.messageFor(name));
    }

}
