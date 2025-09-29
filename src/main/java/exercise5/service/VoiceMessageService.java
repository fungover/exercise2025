package exercise5.service;

import exercise5.repository.MessageRepository;
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
    public void sendMessage(String message) {
        messageRepository.saveMessage(message);
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return "You got a voice message";
    }
}
