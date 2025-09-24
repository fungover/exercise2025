package exercise5;

import exercise5.entities.Book;
import exercise5.entities.Genre;
import exercise5.repsitory.InMemoryBookRepository;

import exercise5.services.Container;
import exercise5.services.DescriptionOfBook;
import exercise5.services.DisplayBooks;
import exercise5.services.SortBooks;

public class Main {

    public static void main(String[] args) {

        //Manual Constructor Injection
        Book book1 = new Book("Book 1", "Author 1", Genre.FEELGOOD, 5);
        Book book2 = new Book("Book 2", "Author 2", Genre.FEELGOOD, 8);
        Book book3 = new Book("Book 3", "Author 3", Genre.FEELGOOD, 7);
        Book book4 = new Book("Book 4", "Author 4", Genre.FEELGOOD, 9);

        InMemoryBookRepository inMemoryBookRepository = new InMemoryBookRepository();
        inMemoryBookRepository.addBook(book1);
        inMemoryBookRepository.addBook(book2);
        inMemoryBookRepository.addBook(book3);
        inMemoryBookRepository.addBook(book4);

        DescriptionOfBook descriptionOfBook = new DescriptionOfBook(book1);
        descriptionOfBook.getDescriptionOfBook();

        SortBooks sortBooks = new SortBooks(inMemoryBookRepository);
        sortBooks.sortBooksByAuthor();
        sortBooks.sortBooksByRating();

        DisplayBooks displayBooks = new DisplayBooks(inMemoryBookRepository);
        displayBooks.displayAllBooks();

        //A Minimal DI Container
        Container container = new Container();

        SortBooks sortBooks2 = container.getAccess(SortBooks.class);
        sortBooks2.sortBooksByRating();

    }

}
