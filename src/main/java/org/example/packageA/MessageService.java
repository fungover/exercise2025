package org.example.packageA;

import org.example.packageC.Source;

public class MessageService {
    Source source;

    public MessageService(Source source) {
        this.source = source;
    }

    public void handleAllMessages(){
        var message = source.getNextMessage();
    }
}
