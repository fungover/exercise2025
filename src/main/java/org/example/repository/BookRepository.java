package org.example.repository;

import org.example.Genre;
import org.example.entities.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends ListCrudRepository<Book,Integer>, PagingAndSortingRepository<Book,Integer> {
    List<Book> findByGenre(Genre genre);

    @EntityGraph("Book.author")
    List<Book> findBooksBy();

    Optional<Book> findBookByTitle(String title);
}
