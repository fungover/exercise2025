package org.example.strategy;

public class AllLowercaseFormatter implements TextFormatter{
    @Override
    public String format(String text) {
        return text.toLowerCase();
    }
}
