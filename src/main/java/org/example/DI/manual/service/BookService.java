package org.example.DI.manual.service;

import org.example.DI.manual.repository.Book;
import org.example.DI.manual.repository.BookRepository;

import java.util.List;


public final class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        bookRepository.save(book);
        System.out.println("Book added");
    }
}
