package exercise5.service;

import exercise5.repository.MessageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.logging.Logger;
@ApplicationScoped
public class EmailMessageService implements MessageService {
    private static final Logger log = Logger.getLogger(EmailMessageService.class.getName());
    MessageRepository messageRepository;
    @Inject
    public EmailMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    @Override
    public void sendMessage(String message) {
        messageRepository.saveMessage(message);
        log.info(message);
    }

    @Override
    public String getMessage() {
        return "You got an email";
    }

}
