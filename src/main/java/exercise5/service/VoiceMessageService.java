package exercise5.service;

import exercise5.enteties.Message;
import exercise5.repository.MessageRepository;
import exercise5.service.MessageService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VoiceMessageService implements MessageService {
    MessageRepository messageRepository;
    @Inject
    public VoiceMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    @Override
    public void sendMessage(Message message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return "This is a voice message";
    }
}
