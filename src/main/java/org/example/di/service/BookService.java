package org.example.di.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.di.notify.Notifier;
import org.example.di.model.Book;
import org.example.di.qualifiers.Console;
import org.example.di.repository.BookRepository;

import java.util.List;

@ApplicationScoped
public class BookService {
    private final BookRepository bookRepository;
    private final  Notifier notifier;

    @Inject
    public BookService(BookRepository bookRepository, @Console Notifier notifier) {
        this.bookRepository = bookRepository;
        this.notifier = notifier;
    }

    public List<Book> listBooks() {
        List<Book> all = bookRepository.findAll();
        notifier.info("Listing " + all.size() + " book(s).");
        return all;
    }

    public void addBook(Book book) {
        bookRepository.save(book);
        notifier.info("Book added: " + book.title() );
    }
}
