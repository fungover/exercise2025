package exercise5;

import exercise5.repository.InMemoryMessageRepository;
import exercise5.repository.MessageRepository;
import exercise5.service.EmailMessageService;
import exercise5.service.MessageService;
import exercise5.service.TextMessageService;
import exercise5.service.VoiceMessageService;
import exercise5.container.Container;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) {
        System.out.println("-----Part 1-----");
        MessageRepository messageRepository1 = new InMemoryMessageRepository(/*new ArrayList<>()*/);
        MessageService textMessageService1 = new TextMessageService(messageRepository1);
        MessageService emailMessageService1 = new EmailMessageService(messageRepository1);
        MessageService voiceMessageService1 = new VoiceMessageService(messageRepository1);
        textMessageService1.sendMessage("This is a sent text");
        log.info(textMessageService1.getMessage());
        emailMessageService1.sendMessage("This is a sent email");
        log.info(emailMessageService1.getMessage());
        voiceMessageService1.sendMessage("This is a sent voice message");
        log.info(voiceMessageService1.getMessage());

        log.info("-----Part 2-----");
        Container container = new Container();
        container.register(MessageService.class, VoiceMessageService.class);
        container.register(MessageRepository.class, InMemoryMessageRepository.class);
        container.register(List.class, ArrayList.class);
        MessageService voiceMessageService2 = container.resolve(VoiceMessageService.class);
        voiceMessageService2.sendMessage("This is a sent voice message");
        log.info(voiceMessageService2.getMessage());

        log.info("-----Part 3-----");
        Weld weld = new Weld();
        try (WeldContainer weldContainer = weld.initialize()) {
            MessageService messageService = weldContainer.select(EmailMessageService.class).get();
            log.info(messageService.getMessage());
        }
    }
}
