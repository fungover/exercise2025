package org.example.packageB;

import org.example.packageC.Source;

public class MessageSource implements Source {
    @Override
    public String getNextMessage() {
        return "Message content";
    }
}
