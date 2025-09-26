package exercise5.services;

import exercise5.entities.Book;
import exercise5.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class DescriptionOfBook implements Description {


    private final BookRepository bookRepository;

    @Inject
    public DescriptionOfBook(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void getDescriptionOfBook() {
        List<Book> books = bookRepository.getAllBooks();

        for (Book book : books) {
            System.out.println("This is a book written by " + book.author() + " with titel of " + book.titel());
        }

    }
}
