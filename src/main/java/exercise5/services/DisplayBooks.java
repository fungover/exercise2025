package exercise5.services;

import exercise5.repsitory.BookRepository;
import exercise5.repsitory.InMemoryBookRepository;

import java.awt.image.IndexColorModel;

public class DisplayBooks implements Library{

    private final InMemoryBookRepository inMemoryBookRepository;

    public DisplayBooks(InMemoryBookRepository inMemoryBookRepository) {
        this.inMemoryBookRepository = inMemoryBookRepository;
    }

    @Override
    public void displayAllBooks() {
        System.out.println("All Books: "+inMemoryBookRepository.getAllBooks());
    }

}
