package exercise5.repsitory;

import exercise5.entities.Book;

import java.util.List;

public interface BookRepository {

    void addBook(Book book);
    List<Book> getAllBooks();

}
