package exercise5.repository;

import exercise5.entities.Book;
import exercise5.entities.Genre;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class InMemoryBookRepository implements BookRepository {

    List<Book> books = new ArrayList<>();

    public InMemoryBookRepository() {
        books.add(new Book("Mormor hälsar och säger förlåt", "Fredrik Backman", Genre.FEELGOOD, 9));
        books.add(new Book("Britt-Marie var här", "Fredrik Backman", Genre.FEELGOOD, 7));
        books.add(new Book("En man som heter Ove", "Fredrik Backman", Genre.FEELGOOD, 10));
        books.add(new Book("Döden i Olivlunden", "Sofia Rutbäck Eriksson", Genre.DECKARE, 8));
        books.add(new Book("Flickan i lådan", "Jan-Erik Ullström", Genre.DECKARE, 5));
    }

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
