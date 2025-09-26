package exercise5.services;

import exercise5.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DisplayBooks implements Library{

    private final BookRepository bookRepository;

    @Inject
    public DisplayBooks(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void displayAllBooks() {
        System.out.println("All Books: "+bookRepository.getAllBooks());
    }

}
