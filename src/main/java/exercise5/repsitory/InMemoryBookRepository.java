package exercise5.repsitory;

import exercise5.entities.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InMemoryBookRepository implements BookRepository {

    List<Book> books = new ArrayList<>();


    @Override
    public void addBook(Book book) {

        Objects.requireNonNull(book);

        books.add(book);
    }

    @Override
    public List<Book> getAllBooks() {

        if (books.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(books);
    }
}
