package exercise5.services;

import exercise5.entities.Book;
import exercise5.repsitory.InMemoryBookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class DescriptionOfBook implements Description {


    private final InMemoryBookRepository inMemoryBookRepository;

    @Inject
    public DescriptionOfBook(InMemoryBookRepository inMemoryBookRepository) {
        this.inMemoryBookRepository = inMemoryBookRepository;
    }

    @Override
    public void getDescriptionOfBook() {
        List<Book> books = inMemoryBookRepository.getAllBooks();

        for (Book book : books) {
            System.out.println("This is a book written by " + book.author() + " with titel of " + book.titel());
        }

    }
}
