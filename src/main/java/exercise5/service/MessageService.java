package exercise5.service;

import exercise5.enteties.Message;

public interface MessageService {
    void sendMessage(Message message);
    String getMessage();
}
