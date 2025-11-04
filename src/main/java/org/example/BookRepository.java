package org.example;

import org.example.entities.Book;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface BookRepository extends ListCrudRepository<Book,Integer> {
Optional<Book> findBookByAuthor(String author);
}
