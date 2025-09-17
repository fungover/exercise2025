package org.example.strategy;

public class AllUppercaseFormatter implements TextFormatter{
    @Override
    public String format(String text) {
        return text.toUpperCase();
    }
}
