package org.example.service;

public class SMSMessageService implements MessageService {
    @Override
    public String getMessage() {
        return "Message from SMS service";
    }
}