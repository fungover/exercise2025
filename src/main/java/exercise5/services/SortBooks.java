package exercise5.services;

import exercise5.entities.Book;
import exercise5.repsitory.BookRepository;

import java.util.Comparator;

public class SortBooks implements Sorting{

    private final BookRepository bookRepository;

    public SortBooks(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void sortBooksByRating() {
        bookRepository.getAllBooks().stream()
                .sorted(Comparator.comparing(Book::rating)).forEach(System.out::println);
    }

    @Override
    public void sortBooksByAuthor() {
        bookRepository.getAllBooks().stream()
                .sorted(Comparator.comparing(Book::author)).forEach(System.out::println);

    }

    @Override
    public void sortBooksByGenre() {
        bookRepository.getAllBooks().stream()
                .sorted(Comparator.comparing(Book::genre)).forEach(System.out::println);
    }

}
