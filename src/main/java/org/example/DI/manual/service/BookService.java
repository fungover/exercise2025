package org.example.DI.manual.service;

import org.example.DI.manual.notify.Notifier;
import org.example.DI.manual.repository.Book;
import org.example.DI.manual.repository.BookRepository;

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
