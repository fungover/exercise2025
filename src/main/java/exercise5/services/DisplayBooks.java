package exercise5.services;

import exercise5.repsitory.BookRepository;

public class DisplayBooks implements Library{

    private final BookRepository bookRepository;

    public DisplayBooks(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void displayAllBooks() {
        System.out.println("All Books: "+bookRepository.getAllBooks());
    }

}
