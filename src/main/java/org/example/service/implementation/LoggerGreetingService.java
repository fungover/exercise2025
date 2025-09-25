package org.example.service.implementation;

import org.example.persistence.MessageRepository;
import org.example.service.GreetingService;
import java.util.logging.Logger;

public class LoggerGreetingService implements GreetingService {
    private static final Logger LOGGER= Logger.getLogger(LoggerGreetingService.class.getName());
    private final MessageRepository messageRepository;

    public LoggerGreetingService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void greet(String name) {
        String template = messageRepository.getMessage();
        LOGGER.info(() -> String.format(template, name));
    }
}
