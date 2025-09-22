package org.example.service;

public class EmailMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "Message from Email service";
    }
}
