package org.example.streams;

public record Guest(String firstName, String lastName) {
    public String fullName() {
        return firstName() + " " + lastName();
    }
}