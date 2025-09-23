package exercise5.part1.service;

import exercise5.part1.enteties.Message;
import exercise5.part1.repository.MessageRepository;

public class VoiceMessageService implements MessageService {
    MessageRepository messageRepository;
    public VoiceMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return "This is a voice message";
    }
}
