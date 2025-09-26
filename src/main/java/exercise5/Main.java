package exercise5;

import exercise5.repsitory.InMemoryBookRepository;
import exercise5.services.Container;
import exercise5.services.DescriptionOfBook;
import exercise5.services.DisplayBooks;
import exercise5.services.SortBooks;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {

    public static void main(String[] args) {

        System.out.println("Manual Constructor Injection");

        InMemoryBookRepository inMemoryBookRepository = new InMemoryBookRepository();

        DescriptionOfBook descriptionOfBook = new DescriptionOfBook(inMemoryBookRepository);
        descriptionOfBook.getDescriptionOfBook();

        SortBooks sortBooks = new SortBooks(inMemoryBookRepository);
        sortBooks.sortBooksByAuthor();
        sortBooks.sortBooksByRating();

        DisplayBooks displayBooks = new DisplayBooks(inMemoryBookRepository);
        displayBooks.displayAllBooks();

        System.out.println("A Minimal DI Container");

        Container container = new Container();

        SortBooks sortBooks2 = container.getAccess(SortBooks.class);
        sortBooks2.sortBooksByRating();
        sortBooks2.sortBooksByGenre();

        DisplayBooks displayBooks2 = container.getAccess(DisplayBooks.class);
        displayBooks2.displayAllBooks();

        System.out.println("Using Weld (CDI)");

        Weld weld = new Weld();

        try(WeldContainer weldContainer = weld.initialize())
        {
            DescriptionOfBook descriptionOfBook1 = weldContainer.select(DescriptionOfBook.class).get();
            descriptionOfBook1.getDescriptionOfBook();
            SortBooks sortBooks1 = weldContainer.select(SortBooks.class).get();
            sortBooks1.sortBooksByRating();
            DisplayBooks  displayBooks1 = weldContainer.select(DisplayBooks.class).get();
            displayBooks1.displayAllBooks();
        }

    }

}
