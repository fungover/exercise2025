package exercise5.service;

import exercise5.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.logging.Logger;

@ApplicationScoped
public class VoiceMessageService implements MessageService {
    MessageRepository messageRepository;
    private static final Logger log = Logger.getLogger(VoiceMessageService.class.getName());
    @Inject
    public VoiceMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    @Override
    public void sendMessage(String message) {
        messageRepository.saveMessage(message);
        log.info(message);
    }

    @Override
    public String getMessage() {
        return "You got a voice message";
    }
}
