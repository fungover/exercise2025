package org.example.di.repository;

import org.example.di.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);
    Optional<Book> findById(String id);
    List<Book> findAll();
}
