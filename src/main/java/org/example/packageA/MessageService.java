package org.example.packageA;

import org.example.packageB.MessageSource;

public class MessageService {
    MessageSource source = new MessageSource();

    public void handleAllMessages(){
        var message = source.getNextMessage();
    }
}
