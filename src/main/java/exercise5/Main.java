package exercise5;

import exercise5.enteties.Message;
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

public class Main {
    public static void main(String[] args) {
        System.out.println("-----Part 1-----");
        List<String> messages = new ArrayList<>();
        MessageRepository messageRepository1 = new InMemoryMessageRepository(messages);
        MessageService textMessageService1 = new TextMessageService(messageRepository1);
        MessageService emailMessageService1 = new EmailMessageService(messageRepository1);
        MessageService voiceMessageService1 = new VoiceMessageService(messageRepository1);
        textMessageService1.sendMessage(new Message("This is a text message"));
        emailMessageService1.sendMessage(new Message("This is an email message"));
        voiceMessageService1.sendMessage(new Message("This is a voice message"));

        System.out.println("-----Part 2-----");
        Container container = new Container();
        container.register(MessageService.class, VoiceMessageService.class);
        container.register(MessageRepository.class, InMemoryMessageRepository.class);
        container.register(List.class, ArrayList.class);
        MessageService voiceMessageService2 = container.resolve(VoiceMessageService.class);
        voiceMessageService2.sendMessage(new Message("This is a voice message"));

        System.out.println("-----Part 3-----");
        System.out.println("Hello World!");
        /*Weld weld = new Weld();
        try(WeldContainer container = weld.initialize()){
            MessageService messageService  = container.select(MessageService.class).get();
        }*/
        Weld weld = new Weld();

        try (WeldContainer weldContainer = weld.initialize()) {
            MessageService messageService = weldContainer.select(MessageService.class).get();
            messageService.sendMessage(new Message("This is a message"));

        }
        /*try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MessageService messageService = container.select(MessageService.class).get();
            messageService.sendMessage("Hello World");
        }*/
    }
}
