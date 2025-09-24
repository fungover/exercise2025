package org.example;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.MessageService;
import org.example.qualifiers.Email;
import org.example.qualifiers.Sms;

public class WeldApp {

    public static void main(String[] args) {

        System.out.println("=== Testing Weld CDI ===");

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) { // Creates and initializes the CDI-container (weld)


            //Gets a bean that implements MessageService with the @Email qualifier.
            //Since EmailMessageService is marked with @Email and @ApplicationScoped,
            //the container knows to provide an instance of EmailMessageService.


            MessageService emailService = container
                    .select(MessageService.class, new Email.Literal())
                    .get();
            emailService.processMessage();

            // Same as above, but now we ask for the @Sms implementation.

            MessageService smsService = container
                    .select(MessageService.class, new Sms.Literal())
                    .get();
            smsService.processMessage();
        }
    }
}
