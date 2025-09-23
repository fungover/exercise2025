package exercise5.part2.service;

import exercise5.part2.repository.MessageRepository;

public class EmailMessageService implements MessageService {
    MessageRepository messageRepository;
    public EmailMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return "This message is an email";
    }
}
