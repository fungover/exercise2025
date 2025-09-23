package exercise5.part2;

import exercise5.part2.container.Container;
import exercise5.part2.repository.InMemoryMessageRepository;
import exercise5.part2.repository.MessageRepository;
import exercise5.part2.service.MessageService;
import exercise5.part2.service.VoiceMessageService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Container container = new Container();
        container.register(MessageService.class, VoiceMessageService.class);
        container.register(MessageRepository.class, InMemoryMessageRepository.class);
        container.register(List.class, ArrayList.class);
        MessageService voiceMessageService = container.resolve(VoiceMessageService.class);
    }
}
