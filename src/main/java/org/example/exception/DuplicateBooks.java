package org.example.exception;

public class DuplicateBooks extends RuntimeException{
    public DuplicateBooks(String message){
        super(message);
    }
}
