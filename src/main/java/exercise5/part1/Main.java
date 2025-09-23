package exercise5.part1;

import exercise5.part1.enteties.Message;
import exercise5.part1.repository.InMemoryMessageRepository;
import exercise5.part1.repository.MessageRepository;
import exercise5.part1.service.EmailMessageService;
import exercise5.part1.service.MessageService;
import exercise5.part1.service.TextMessageService;
import exercise5.part1.service.VoiceMessageService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> messages = new ArrayList<>();
        MessageRepository messageRepository = new InMemoryMessageRepository(messages);
        MessageService textMessageService = new TextMessageService(messageRepository);
        MessageService emailMessageService = new EmailMessageService(messageRepository);
        MessageService voiceMessageService = new VoiceMessageService(messageRepository);
        textMessageService.sendMessage("This is a text message");
        emailMessageService.sendMessage("This is an email message");
        voiceMessageService.sendMessage("This is a voice message");

    }
}
