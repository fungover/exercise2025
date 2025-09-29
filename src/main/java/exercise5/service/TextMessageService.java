package exercise5.service;

import exercise5.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TextMessageService implements MessageService {
    private final MessageRepository messageRepository;
    @Inject
    public TextMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void sendMessage(String message) {
        messageRepository.saveMessage(message);
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return "You got a text message";
    }
}
