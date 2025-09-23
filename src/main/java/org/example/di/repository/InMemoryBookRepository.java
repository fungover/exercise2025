package org.example.di.repository;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.di.model.Book;

import java.util.*;

@ApplicationScoped
public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> books = new HashMap<>();

    @Override
    public void save(Book book) {
        books.put(book.id(), book);
    }

    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
}
