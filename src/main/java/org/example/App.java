package org.example;

import org.example.packageA.MessageService;
import org.example.packageB.MessageSource;
import org.example.packageC.Source;

public class App {
    public static void main(String[] args) {
        Source source = new MessageSource();
        MessageService service = new MessageService(source);
    }
}
