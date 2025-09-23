package org.example.service;

import jakarta.enterprise.context.Dependent;

@Dependent
public class EmailMessageService implements MessageService {
    public EmailMessageService() {}

    @Override
    public String getMessage() {
        return "Message from Email service";
    }
}
