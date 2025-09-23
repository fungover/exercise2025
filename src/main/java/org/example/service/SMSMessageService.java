package org.example.service;

import jakarta.enterprise.context.Dependent;
import org.example.qualifier.SMS;

@SMS
@Dependent
public class SMSMessageService implements MessageService {

    @Override
    public String getMessage() {
        return "Message from SMS service";
    }
}