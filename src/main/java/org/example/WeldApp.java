package org.example;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.domain.MessageService;
import org.example.qualifiers.Email;
import org.example.qualifiers.Sms;

public class WeldApp {

    public static void main(String[] args) {

        System.out.println("=== Testing Weld CDI ===");


        /**
         * Creates and starts the Weld CDI-container.
         * - It scans the classes in the project after CDI annotations. (@ApplicationScoped, @Inject, @Qualifier etc.)
         * - Then it builds a "map" of which implementation to use for each interface/abstraction. (Which beans to use for which injection points)
         */
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {


            /**
             * Here we ask for MessageService with the @Email qualifier (annotation).
             * - CDI finds EmailMessageService (It's applicationScoped and has @Email annotation).
             * - Since EmailMessageService has a dependency on DataRepository, it also creates a DatabaseRepository.
             * - NOTE: Selection of repository depends on which one is active in beans.xml (Database or File). @Default or @Alternative.
             */

            MessageService emailService = container
                    .select(MessageService.class, new Email.Literal())
                    .get();
            emailService.processMessage();

            /**
             * Now we ask for MessageService with the @Sms qualifier (annotation) instead.
             * - CDI finds SmsMessageService (It's applicationScoped and has @Sms annotation).
             * - This one also needs a DataRepository, but now we reuse the same DatabaseRepository instance as before (because it's applicationScoped).
             */

            MessageService smsService = container
                    .select(MessageService.class, new Sms.Literal())
                    .get();
            smsService.processMessage();
        }
        /**
         * And here at the end the container is shut down automatically (try-with-resources).
         * - All beans with scope @ApplicationScoped or @Dependent are destroyed.
         */
    }
}
