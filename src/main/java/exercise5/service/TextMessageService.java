package exercise5.service;

import exercise5.enteties.Message;
import exercise5.repository.MessageRepository;
import exercise5.service.MessageService;
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
    public void sendMessage(Message message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return "This message is an SMS";
    }
}
