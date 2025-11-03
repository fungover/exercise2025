package org.example;

import org.example.entities.Book;
import org.springframework.data.repository.ListCrudRepository;

public interface BookRepository extends ListCrudRepository<Book,Integer> {

}
