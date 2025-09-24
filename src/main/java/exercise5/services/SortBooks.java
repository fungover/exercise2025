package exercise5.services;

import exercise5.entities.Book;
import exercise5.repsitory.InMemoryBookRepository;

import java.util.Comparator;

public class SortBooks implements Sorting{

    private final InMemoryBookRepository inMemoryBookRepository;

    public SortBooks(InMemoryBookRepository inMemoryBookRepository) {
        this.inMemoryBookRepository = inMemoryBookRepository;
    }

    @Override
    public void sortBooksByRating() {
        inMemoryBookRepository.getAllBooks().stream()
                .sorted(Comparator.comparing(Book::rating)).forEach(System.out::println);
    }

    @Override
    public void sortBooksByAuthor() {
        inMemoryBookRepository.getAllBooks().stream()
                .sorted(Comparator.comparing(Book::author)).forEach(System.out::println);

    }

    @Override
    public void sortBooksByGenre() {
        inMemoryBookRepository.getAllBooks().stream()
                .sorted(Comparator.comparing(Book::genre)).forEach(System.out::println);
    }

}
