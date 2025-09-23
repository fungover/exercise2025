package org.example.di.service;

import org.example.di.notify.Notifier;
import org.example.di.model.Book;
import org.example.di.repository.BookRepository;

import java.util.List;


public final class BookService {
    private final BookRepository bookRepository;
    private final Notifier notifier;

    public BookService(BookRepository bookRepository, Notifier notifier) {
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
