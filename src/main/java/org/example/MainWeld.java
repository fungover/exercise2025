package org.example;

import org.example.service.MessageService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class MainWeld {
    static void main(String[] args) {
        // Start
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {

            MessageService service = container.select(MessageService.class).get();

            service.sendMessage("Hej från Weld CDI!");

        }
    }
}

/*
Compared to Part 1 and 2, I find Weld much "easier" (nothing is easy at this point but it's more understandable maybe, hehe.)
No need to create a custom container or manually map interfaces,
Weld handles the injection automatically, making the code easier to follow.
*/
